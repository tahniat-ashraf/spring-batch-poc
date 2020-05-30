//package com.priyam.springbatch.poc;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
//import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
//import org.springframework.batch.item.ItemProcessor;
//import org.springframework.batch.item.file.FlatFileItemReader;
//import org.springframework.batch.item.file.FlatFileItemWriter;
//import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
//import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
//import org.springframework.batch.item.file.mapping.FieldSetMapper;
//import org.springframework.batch.item.file.transform.FieldSet;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.core.io.FileSystemResource;
//
///**
// * @author Tahniat Ashraf Priyam
// * @since 5/28/20
// */
//@Configuration
//@RequiredArgsConstructor
//@EnableBatchProcessing
//public class FileBatchConfiguration {
//
//    private final JobBuilderFactory jobBuilderFactory;
//    private final StepBuilderFactory stepBuilderFactory;
//
//    @Bean
//    public Job job() {
//        return jobBuilderFactory
//                .get("spring-batch-poc-job4")
//                .flow(step())
//                .end()
//                .build();
//    }
//
//    @Bean
//    public Step step() {
//        return stepBuilderFactory
//                .get("spring-batch-poc-step1")
//                .<Customer, Customer>chunk(1000)
//                .faultTolerant()
//                .reader(reader())
////                .processor(processor())
//                .writer(writer())
//                .build();
//    }
//
////    private JdbcBatchItemWriter<String> writer() {
////        return null;
////    }
//
//
//    private ItemProcessor<? super String, ? extends String> processor() {
//        return null;
//    }
//
//    @Bean
//    public FlatFileItemReader<Customer> reader() {
//
//        return new FlatFileItemReaderBuilder<Customer>()
//                .name("customerItemReader1")
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
//    public FlatFileItemWriter<Customer> writer() {
//        return new FlatFileItemWriterBuilder<Customer>()
//                .name("customerItemWriter1")
//                .resource(new FileSystemResource("output.csv"))
//                .delimited()
//                .fieldExtractor(new CustomFieldExtractor())
////                .names("firstName", "lastName", "birthdate")
//                .build();
//    }
//
//    //*************************
//    //This approach works to!!!
//    //*************************
//
////    @Bean
////    public FlatFileItemReader<Customer> reader() {
////
////        FlatFileItemReader<Customer> flatFileItemReader = new FlatFileItemReader<>();
////
////        flatFileItemReader.setLinesToSkip(1);
////        flatFileItemReader.setResource(new ClassPathResource("sample-data.csv"));
////        DefaultLineMapper<Customer> lineMapper = new DefaultLineMapper<>();
////        lineMapper.setLineTokenizer(new DelimitedLineTokenizer());
////        lineMapper.setFieldSetMapper(new CustomerFieldSetMapper());
////        flatFileItemReader.setLineMapper(lineMapper);
////
////        return flatFileItemReader;
////    }
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
//            customer.setBirthDate(fieldSet.readDate("birthdate"));
//
//
//            return customer;
//        }
//    }
//}
