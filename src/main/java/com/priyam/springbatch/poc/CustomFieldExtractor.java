package com.priyam.springbatch.poc;

import org.springframework.batch.item.file.transform.FieldExtractor;

/**
 * @author Tahniat Ashraf Priyam
 * @since 5/29/20
 */
public class CustomFieldExtractor implements FieldExtractor<Customer> {
    @Override
    public Object[] extract(Customer item) {
        Object[] objects = new Object[4];
        objects[0] = item.getId();
        objects[1] = item.getFirstName();
        objects[2] = item.getFirstName();
        objects[3] = item.getBirthDate();

        return objects;
    }
}
