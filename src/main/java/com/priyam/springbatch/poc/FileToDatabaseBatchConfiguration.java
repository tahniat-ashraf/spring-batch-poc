//package com.priyam.springbatch.poc;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.batch.core.ChunkListener;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
//import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
//import org.springframework.batch.core.scope.context.ChunkContext;
//import org.springframework.batch.item.ItemProcessor;
//import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
//import org.springframework.batch.item.database.JdbcBatchItemWriter;
//import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
//import org.springframework.batch.item.file.FlatFileItemReader;
//import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
//import org.springframework.batch.item.file.mapping.FieldSetMapper;
//import org.springframework.batch.item.file.transform.FieldSet;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.ClassPathResource;
//
//import javax.sql.DataSource;
//
///**
// * @author Tahniat Ashraf Priyam
// * @since 5/28/20
// */
//@Configuration
//@RequiredArgsConstructor
//@EnableBatchProcessing
//public class FileToDatabaseBatchConfiguration {
//
//    private final JobBuilderFactory jobBuilderFactory;
//    private final StepBuilderFactory stepBuilderFactory;
//    private final DataSource dataSource;
//
//    @Bean
//    public Job job() {
//        return jobBuilderFactory
//                .get("spring-batch-poc-f2db-3")
//                .flow(step())
//                .end()
//                .build();
//    }
//
//    @Bean
//    public Step step() {
//        return stepBuilderFactory
//                .get("spring-batch-poc-f2db-step-1")
//                .<Customer, Customer>chunk(1000)
//                .faultTolerant()
//                .reader(reader())
////                .processor(processor())
//                .processor(processor())
//                .writer(writer())
//                .listener(new ChunkListener() {
//                    @Override
//                    public void beforeChunk(ChunkContext context) {
//                        System.out.println("beforeChunk");
//                    }
//
//                    @Override
//                    public void afterChunk(ChunkContext context) {
//                        System.out.println("afterChunk");
//                    }
//
//                    @Override
//                    public void afterChunkError(ChunkContext context) {
//
//                    }
//                })
//                .build();
//    }
//
//    private ItemProcessor<Customer, Customer> processor() {
//        return item -> {
//            item.setRandomNumber("" + (Math.floor(Math.random() * 100) + 1));
//            return item;
//        };
//    }
//
////    private JdbcBatchItemWriter<String> writer() {
////        return null;
////    }
//
//
//    @Bean
//    public FlatFileItemReader<Customer> reader() {
//
//        return new FlatFileItemReaderBuilder<Customer>()
//                .name("customerItemReader")
//                .resource(new ClassPathResource("sample-data.csv"))
//                .linesToSkip(1)
//                .delimited()
//                .names("id", "firstName", "lastName", "birthdate")
//                .fieldSetMapper(new CustomerFieldSetMapper())//This also works
////                .fieldSetMapper(fieldSetMapper())
//                .build();
//
//
//    }
//
//
//    @Bean
//    public JdbcBatchItemWriter<Customer> writer() {
//        return new JdbcBatchItemWriterBuilder<Customer>()
//                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
//                .sql("INSERT INTO customer (first_name, last_name, random_number, birth_date) VALUES (:firstName, :lastName, :randomNumber, :birthDate)")
//                .dataSource(dataSource)
//                .build();
//    }
//
//
//    private static class CustomerFieldSetMapper implements FieldSetMapper<Customer> {
//
//        @Override
//        public Customer mapFieldSet(FieldSet fieldSet) {
//            Customer customer = new Customer();
//
//            customer.setId(fieldSet.readLong("id"));
//            customer.setFirstName(fieldSet.readString("firstName"));
//            customer.setLastName(fieldSet.readString("lastName"));
//            customer.setBirthDate(fieldSet.readString("birthdate"));
//
//
//            return customer;
//        }
//    }
//}
