package com.yurentsy.watchingyou.mvp.model.cache;

import com.yurentsy.watchingyou.mvp.model.entity.Person;

import java.util.List;

import io.reactivex.Observable;

public interface Cache {
    void putPerson(Person person);

    void putAllPersons(List<Person> list);

    Observable<Person> getPersonById(String id);

    Observable<List<Person>> getPersons();
}
