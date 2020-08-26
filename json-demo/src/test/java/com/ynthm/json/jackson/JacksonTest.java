package com.ynthm.json.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.ynthm.benchmark.entity.Hobby;
import com.ynthm.common.Result;
import org.junit.jupiter.api.Test;

import java.util.*;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JacksonTest {
  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  @Test
  void testJackson() throws JsonProcessingException {
    List<Hobby> list = new ArrayList<>();
    list.add(new Hobby().setName("电影").setTags(new ArrayList<>(Arrays.asList("123", "456"))));
    list.add(new Hobby().setName("电视剧").setTags(new ArrayList<>(Arrays.asList("123", "456"))));
    String s = OBJECT_MAPPER.writeValueAsString(list);
    List<Hobby> list1 = OBJECT_MAPPER.readValue(s, new TypeReference<List<Hobby>>() {});
    assertThat(list1).containsExactlyElementsIn(list);

    JavaType javaType =
        OBJECT_MAPPER.getTypeFactory().constructParametricType(List.class, Hobby.class);
    List<Hobby> o = OBJECT_MAPPER.readValue(s, javaType);

    assertThat(o).containsExactlyElementsIn(list);
  }

  @Test
  void test1() throws JsonProcessingException {

    Result<Hobby> result =
        Result.ok(
            new Hobby().setName("电影").setTags(new ArrayList<>(Arrays.asList("肖申克的救赎", "盗梦空间"))));
    String s = OBJECT_MAPPER.writeValueAsString(result);
    JavaType javaType =
        OBJECT_MAPPER.getTypeFactory().constructParametricType(Result.class, Hobby.class);

    Result<Hobby> o = OBJECT_MAPPER.readValue(s, javaType);
    assertThat(o).isEqualTo(result);

    ArrayList<HashSet<Integer>> listSet =
        Lists.newArrayList(Sets.newHashSet(1, 2), Sets.newHashSet(2, 3));
    String listSetStr = OBJECT_MAPPER.writeValueAsString(listSet);

    JavaType inner =
        OBJECT_MAPPER.getTypeFactory().constructParametricType(HashSet.class, Integer.class);
    JavaType javaType1 =
        OBJECT_MAPPER.getTypeFactory().constructParametricType(ArrayList.class, inner);

    ArrayList<HashSet<Integer>> listSet2 = OBJECT_MAPPER.readValue(listSetStr, javaType1);
    assertThat(listSet2).hasSize(2);
  }

  @Test
  void testParametricTypes() {
    TypeFactory tf = TypeFactory.defaultInstance();
    // first, simple class based
    JavaType t = tf.constructParametricType(ArrayList.class, String.class); // ArrayList<String>
    assertThat(CollectionType.class).isEqualTo(t.getClass());
    assertThat(t.containedTypeCount()).isEqualTo(1);
    assertThat(t.containedType(1)).isNull();
    JavaType strC = tf.constructType(String.class);
    assertThat(strC).isEqualTo(t.containedType(0));

    // Then using JavaType
    JavaType t2 = tf.constructParametricType(Map.class, strC, t); // Map<String,ArrayList<String>>
    // should actually produce a MapType
    assertThat(MapType.class).isEqualTo(t2.getClass());
    assertThat(t2.containedTypeCount()).isEqualTo(2);
    assertThat(t2.containedType(0)).isEqualTo(strC);
    assertThat(t2.containedType(1)).isEqualTo(t);
    assertThat(t2.containedType(2)).isNull();
    // Maps must take 2 type parameters, not just one
    Throwable throwable =
        assertThrows(
            IllegalArgumentException.class, () -> tf.constructParametricType(Map.class, strC));
    assertThat(throwable.getMessage())
        .contains(
            "Cannot create TypeBindings for class java.util.Map with 1 type parameter: class expects 2");
  }
}
