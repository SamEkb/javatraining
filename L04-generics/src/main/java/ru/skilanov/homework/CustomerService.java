package ru.skilanov.homework;


import java.util.AbstractMap;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class CustomerService {
    private final TreeMap<Customer, String> customersMap = new TreeMap<>(Comparator.comparing(Customer::getScores));

    public Map.Entry<Customer, String> getSmallest() {
        return getEntry(customersMap.firstEntry());
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        return getEntry(customersMap.higherEntry(customer));
    }

    public void add(Customer customer, String data) {
        customersMap.put(customer, data);
    }

    private Map.Entry<Customer, String> getEntry(Map.Entry<Customer, String> entry) {
        return (entry == null) ? null : new AbstractMap.SimpleImmutableEntry<>(
                new Customer(entry.getKey().getId(), entry.getKey().getName(), entry.getKey().getScores()),
                entry.getValue()
        );
    }
}
