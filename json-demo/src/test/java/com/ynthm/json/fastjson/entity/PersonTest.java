package com.ynthm.json.fastjson.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;

class PersonTest {
  private List<Person> listOfPersons = new ArrayList<>();

  @BeforeEach
  void setUp() {
    listOfPersons.add(
        new Person()
            .setAge(18)
            .setFirstName("Ethan")
            .setLastName("Wang")
            .setBirthDay(LocalDate.now().plusYears(18)));
    listOfPersons.add(
        new Person()
            .setAge(30)
            .setFirstName("JunYao")
            .setLastName("Liu")
            .setBirthDay(LocalDate.now().plusYears(30)));
  }

  @Test
  void javaList_coverToJson() {
    String jsonOutput = JSON.toJSONString(listOfPersons);
    assertThat(jsonOutput).contains("FIRST NAME");
    List<Person> personList = JSON.parseArray(jsonOutput, Person.class);
    assertThat(personList).hasSize(2);
    String jsonOutput1 = JSON.toJSONString(listOfPersons, SerializerFeature.BeanToArray);
    System.out.println(jsonOutput1);
  }

  @Test
  void whenGenerateJson_thanGenerationCorrect() {
    JSONArray jsonArray = new JSONArray();
    for (int i = 0; i < 2; i++) {
      JSONObject jsonObject = new JSONObject();
      jsonObject.put("AGE", 10);
      jsonObject.put("FULL NAME", "Doe " + i);
      jsonObject.put("DATE OF BIRTH", "2016/12/12");
      jsonArray.add(jsonObject);
    }

    String jsonOutput = jsonArray.toJSONString();
    JSONArray objects = JSON.parseArray(jsonOutput);
    assertThat(objects.size()).isEqualTo(2);

    assertThat(jsonOutput).contains("FULL");
  }
}
