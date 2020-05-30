package com.priyam.springbatch.poc;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.*;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Tahniat Ashraf Priyam
 * @since 5/28/20
 */
@Configuration
@RequiredArgsConstructor
@EnableBatchProcessing
public class DatabaseBatchConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final DataSource dataSource;

    @Bean
    public Job job() {
        return jobBuilderFactory
                .get("spring-batch-poc-db2db-4")
                .flow(step())
                .end()
                .build();
    }

    @Bean
    public Step step() {
        return stepBuilderFactory
                .get("spring-batch-poc-f2db-step-1")
                .<Customer, Customer>chunk(10)
                .faultTolerant()
                .reader(reader())
//                .processor(processor())
                .processor(processor())
                .writer(writer())
                .listener(new ChunkListener() {
                    @Override
                    public void beforeChunk(ChunkContext context) {
                        System.out.println("beforeChunk");
                    }

                    @Override
                    public void afterChunk(ChunkContext context) {
                        System.out.println("afterChunk");
                    }

                    @Override
                    public void afterChunkError(ChunkContext context) {

                    }
                })
                .build();
    }

    private ItemProcessor<Customer, Customer> processor() {
        return item -> {
            item.setRandomNumber("" + item.getId());
            return item;
        };
    }

//    private JdbcBatchItemWriter<String> writer() {
//        return null;
//    }


    @Bean
    public JdbcPagingItemReader<Customer> reader() {
        Map<String, Object> parameterValues = new HashMap<>();
        parameterValues.put("id", "20");

        try {
            return new JdbcPagingItemReaderBuilder<Customer>()
                    .name("creditReader")
                    .dataSource(dataSource)
                    .queryProvider(queryProvider())
                    .parameterValues(parameterValues)
                    .rowMapper(new CustomerFieldSetMapper())
                    .pageSize(10)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Bean
    public PagingQueryProvider queryProvider() {
        MySqlPagingQueryProvider provider = new MySqlPagingQueryProvider();

        provider.setSelectClause("select id, first_name, last_name, birth_date");
        provider.setFromClause("from customer");
        provider.setWhereClause("where id<:id");
        provider.setSortKeys(sortByIdAsc());

        return provider;
    }


    private Map<String, Order> sortByIdAsc() {
        Map<String, Order> sortConfiguration = new HashMap<>();
        sortConfiguration.put("id", Order.ASCENDING);
        return sortConfiguration;
    }


    @Bean
    public JdbcBatchItemWriter<Customer> writer() {
        return new JdbcBatchItemWriterBuilder<Customer>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
//                .sql("INSERT INTO customer (first_name, last_name, random_number, birth_date) VALUES (:firstName, :lastName, :randomNumber, :birthDate)")
                .sql("UPDATE customer SET random_number=:randomNumber WHERE id=:id")
                .dataSource(dataSource)
                .build();
    }


    private static class CustomerFieldSetMapper implements RowMapper<Customer> {


        @Override
        public Customer mapRow(ResultSet resultSet, int i) throws SQLException {
            Customer customer = new Customer();

            customer.setId(resultSet.getLong("id"));
            customer.setFirstName(resultSet.getString("first_name"));
            customer.setLastName(resultSet.getString("last_name"));
            customer.setBirthDate(resultSet.getString("birth_date"));


            return customer;
        }
    }
}
