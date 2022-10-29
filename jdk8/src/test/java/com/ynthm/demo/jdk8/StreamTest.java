package com.ynthm.demo.jdk8;

import com.ynthm.demo.jdk8.bean.Author;
import com.ynthm.demo.jdk8.bean.Role;
import com.ynthm.demo.jdk8.bean.User;
import com.ynthm.demo.jdk8.concurrent.SplitIteratorThread;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamTest {

  /** 构造流的集中方式 */
  @Test
  void testInit() {

    // 1. Individual values 单独值 java.util.stream.Stream.of(T...)
    Stream<String> stream = Stream.of("a", "b", "c");
    stream.forEach(System.out::println);

    // 2. Arrays 数组
    String[] strArray = new String[] {"apple", "banana"};
    stream = Stream.of(strArray);
    stream.forEach(System.out::println);
    stream = Arrays.stream(strArray);
    System.out.println(stream.collect(Collectors.joining(",")));

    // 3. 集合 java.util.Collection.stream
    List<String> list = Arrays.asList(strArray);
    stream = list.stream();
    stream.forEach(System.out::println);
  }

  /**
   * IntStream, LongStream, DoubleStream. 当然也可以用Stream<Integer>, Stream<Long>, Stream<Double>,
   * 但是boxing 和 unboxing会很耗时, 所以特别为这三个基本数值型提供了对应的Stream
   */
  @Test
  void basicStream() {
    IntStream.of(new int[] {1, 2, 3}).forEach(System.out::print);
    System.out.println();
    IntStream.range(4, 6).forEach(System.out::print);
    System.out.println();
    IntStream.rangeClosed(1, 3).forEach(System.out::print);
  }

  @Test
  void streamExchange() {
    Supplier<Stream<String>> streamSupplier = () -> Stream.of("A", "B", "C");
    // 1. Array
    Stream<String> stream = streamSupplier.get();
    String[] strArray1 = stream.toArray(String[]::new);
    for (String s : strArray1) {
      System.out.println(s);
    }

    // 2.Collection list
    stream = streamSupplier.get(); // stream has already been operated upon or closed
    List<String> list1 = stream.collect(Collectors.toList());
    System.out.println(list1);

    // 2.Collection list
    stream = streamSupplier.get();
    List<String> list2 = stream.collect(Collectors.toCollection(ArrayList::new));
    System.out.println(list2);

    // 2.Collection set
    stream = streamSupplier.get();
    Set<String> set = stream.collect(Collectors.toSet());
    System.out.println(set);

    // 2.Collection stack
    stream = streamSupplier.get();
    Stack<String> stack = stream.collect(Collectors.toCollection(Stack::new));
    System.out.println(stack);

    // 3. String
    stream = streamSupplier.get();
    String str = stream.collect(Collectors.joining());
    System.out.print(str); // a1b1c1
  }

  @Test
  void testFilter() {

    // A Stream should be operated on (invoking an intermediate or terminal stream operation) only
    // once. A Stream implementation may throw IllegalStateException if it detects that the Stream
    // is being reused.
    // 可以用 Supplier 解决这个问题
    Supplier<Stream<String>> streamSupplier = () -> Stream.of("apple", "banana", "dog");
    List<String> fruits =
        streamSupplier.get().filter(e -> !Objects.equals(e, "dog")).collect(Collectors.toList());
    System.out.println(fruits);
    // java.lang.IllegalStateException: stream has already been operated upon or closed
    Optional<String> result2 = streamSupplier.get().findFirst();
    System.out.println(result2.get());

    Integer[] arr = new Integer[] {1, 4, 2, 5, 6, 3};
    Stream.of(arr).findFirst().ifPresent(System.out::println);
    Integer abc = Stream.of(arr).filter(i -> i > 3).findFirst().orElse(null);
    System.out.println(abc);
  }

  @Test
  void testToMap() {
    List<User> list = new ArrayList<>();
    User user1 = new User();
    user1.setAge(10);
    user1.setName("abc");
    list.add(user1);

    User user2 = new User();
    user2.setAge(2);
    user2.setName("123");
    list.add(user2);
    // 注意 List 中存在相同的Map key 就会报错
    Map<String, Integer> name2AgeMap =
        list.stream().collect(Collectors.toMap(User::getName, User::getAge));
    System.out.println(name2AgeMap);
    Map<String, User> name2UserMap = list.stream().collect(Collectors.toMap(User::getName, u -> u));
    System.out.println(name2UserMap);
    list.stream().findFirst().ifPresent(System.out::println);
  }

  @Test
  void map1() {
    List<Author> list = new ArrayList<>();
    list.add(new Author(1, "Ethan"));
    list.add(new Author(2, "Wang"));
    System.out.println(list);

    List<User> userList =
        list.stream()
            .map(
                e -> {
                  User user = new User();
                  user.setId(e.getId());
                  user.setName(e.getName());
                  return user;
                })
            .collect(Collectors.toList());
    System.out.println(userList);
  }

  @Test
  void mapToUpperCase() {
    Stream<String> stream = Stream.of("hello", "world", "java8", "stream");
    List<String> wordList = stream.map(String::toUpperCase).collect(Collectors.toList());
    System.out.println(wordList.toString());

    stream = Stream.of("hello", "stream");
    wordList = stream.map(s -> s.toUpperCase()).collect(Collectors.toList());
    System.out.println(wordList.toString());
  }

  @Test
  void flatMap() {
    Stream<List<Integer>> inputStream =
        Stream.of(Arrays.asList(1), Arrays.asList(2, 3), Arrays.asList(4, 5, 6));
    Stream<Integer> outputStream = inputStream.flatMap(childList -> childList.stream());
    System.out.print(outputStream.collect(Collectors.toList()).toString());
  }

  @Test
  void readFilePickAllWords() {
    Path path =
        Paths.get(System.getProperty("user.dir") + "/src/test/java/com/ynthm/jdk/FloatTest.java");

    // 1. Java 8 Read File + Stream
    try (Stream<String> stream = Files.lines(path)) {
      List<String> output =
          stream
              .flatMap(line -> Stream.of(line.split(" ")))
              .filter(word -> word.length() > 0)
              .collect(Collectors.toList());
      System.out.println(output);
    } catch (IOException e) {
      e.printStackTrace();
    }

    // 2. BufferedReader + Stream
    try (BufferedReader br = Files.newBufferedReader(path)) {
      List<String> output =
          br.lines()
              .flatMap(line -> Stream.of(line.split(" ")))
              .map(String::toUpperCase)
              .distinct()
              .sorted()
              .filter(word -> word.length() > 0)
              .collect(Collectors.toList());
      System.out.println(output);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  void sort() {
    // 自然顺序排序 基本数据类型 字符串
    List<String> collect =
        Stream.of("1", "5", "2", "9", "3", "4").sorted().collect(Collectors.toList());
    System.out.println(collect);

    List<User> list = new ArrayList<>();
    User user1 = new User();
    user1.setAge(10);
    user1.setName("abc");
    list.add(user1);

    User user2 = new User();
    user2.setAge(2);
    user2.setName("123");
    list.add(user2);
    // 自然顺序排序
    List<User> userList =
        list.stream()
            .sorted(Comparator.comparingInt(User::getAge).reversed())
            .collect(Collectors.toList());
    System.out.println(userList);

    list.sort(Comparator.comparingInt(User::getAge).thenComparing(User::getName));
    list.forEach(System.out::println);

    String names = list.stream().map(User::getName).collect(Collectors.joining(";"));
    System.out.println(names);
  }

  @Test
  void sort1() {
    List<User> users = new ArrayList<>();
    User user;
    for (int i = 0; i < 5; i++) {
      user = new User();
      user.setId(i);
      user.setName("a" + i);
      users.add(user);
    }
    List<String> userList =
        users.stream()
            .sorted(Comparator.comparing(User::getName).reversed())
            .map(User::getName)
            .limit(2)
            .collect(Collectors.toList());
    System.out.println(userList);

    List<String> userList1 =
        users.stream()
            .limit(2)
            .sorted(Comparator.comparing(User::getName).reversed())
            .map(User::getName)
            .collect(Collectors.toList());
    System.out.println(userList1);
  }

  @Test
  void readFileFindLongestLine() {
    Path path =
        Paths.get(System.getProperty("user.dir") + "/src/test/java/com/ynthm/jdk/FloatTest.java");

    // 1. Java 8 Read File + Stream
    try (Stream<String> stream =
        Files.lines(path).onClose(() -> System.out.println("File closed"))) {
      int output = stream.mapToInt(String::length).max().getAsInt();
      System.out.println(output);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  void skipAndLimit() {
    List<String> list =
        Stream.of("1", "5", "2", "9", "3", "4").skip(2).limit(3).collect(Collectors.toList());
    System.out.println(list);
  }

  @Test
  void top10() {

    Map<String, Integer> map = new HashMap<>();
    map.put("A", 2);
    map.put("B", 5);
    map.put("C", 3);
    map.put("D", 4);
    map.put("E", 1);
    map.entrySet().stream()
        .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
        .limit(3)
        .forEach(System.out::println);
  }

  @Test
  void keepEvenNumber() {
    Integer[] sixNums = {1, 2, 3, 4, 5, 6};
    Integer[] evens = Stream.of(sixNums).filter(n -> n % 2 == 0).toArray(Integer[]::new);
    System.out.println(Arrays.toString(evens));
  }

  @Test
  void peek() {
    List<String> list =
        Stream.of("one", "two", "three", "four")
            .filter(p -> p.length() > 3)
            .peek(v -> System.out.println("Filtered Value:" + v))
            .map(String::toUpperCase)
            .peek(v -> System.out.println("Mapped Value:" + v))
            .collect(Collectors.toList());

    System.out.println(list);
  }

  @Test
  void reduce() {
    // 1. 求和 SUM 10
    Integer sum = Stream.of(1, 2, 3, 4).reduce(0, (a, b) -> a + b);
    System.out.println(sum);
    sum = Stream.of(1, 2, 3, 4).reduce(1, Integer::sum); // 有起始值
    System.out.println(sum);
    sum = Stream.of(1, 2, 3, 4).reduce(Integer::sum).get(); // 无起始值
    System.out.println(sum);

    // 2. 最小值 minValue = -3.0
    double minValue = Stream.of(-1.5, 1.0, -3.0, -2.0).reduce(Double.MAX_VALUE, Double::min);
    System.out.println(minValue);
    minValue = Stream.of(-1.5, 1.0, -3.0, -2.0).reduce(Double::min).get();
    System.out.println(minValue);

    // 2. 最大数值 maxValue = 1.0
    double maxValue = Stream.of(-1.5, 1.0, -3.0, -2.0).reduce(Double.MIN_VALUE, Double::max);
    System.out.println(maxValue);
    maxValue = Stream.of(-1.5, 1.0, -3.0, -2.0).reduce(Double::max).get();
    System.out.println(maxValue);

    // 3. 字符串连接
    String concat = Stream.of("A", "B", "C", "D").reduce("", String::concat);
    System.out.println(concat);
    // 4. 过滤和字符串连接 Filter & Concat
    concat =
        Stream.of("a", "B", "c", "D", "e", "F")
            .filter(x -> x.compareTo("Z") > 0)
            .reduce("", String::concat);
    System.out.println(concat);
  }

  /** streams of primitives. 直接 collect 会报错 */
  @Test
  void boxed() {

    List<Integer> ints = IntStream.of(1, 2, 3, 4, 5).boxed().collect(Collectors.toList());

    System.out.println(ints);

    List<Integer> ints1 =
        IntStream.of(1, 2, 3, 4, 5).mapToObj(Integer::valueOf).collect(Collectors.toList());

    System.out.println(ints1);

    int[] intArray = IntStream.of(1, 2, 3, 4, 5).toArray();

    System.out.println(Arrays.toString(intArray));

    Optional<Integer> max = IntStream.of(1, 2, 3, 4, 5).boxed().max(Integer::compareTo);

    System.out.println(max);
  }

  @Test
  void joining() {
    List<String> words = Arrays.asList("A", "B", "C", "D");

    String joinedString = words.stream().collect(Collectors.joining()); // ABCD
    System.out.println(joinedString);

    joinedString = words.stream().collect(Collectors.joining(",")); // A,B,C,D
    System.out.println(joinedString);
    System.out.println(String.join(",", words));

    joinedString = words.stream().collect(Collectors.joining(",", "{", "}")); // {A,B,C,D}
    System.out.println(joinedString);
  }

  @Test
  void match() {
    List<User> users = new ArrayList<>();

    for (int i = 1; i < 6; i++) {
      User user = new User();
      user.setId(i);
      user.setName("name_" + i);
      user.setAge(new Random().nextInt(80));
      users.add(user);
    }

    boolean isAllAdult =
        users.stream()
            .allMatch(
                p -> {
                  // 打印了第一个 User
                  System.out.println(p.getAge());
                  return p.getAge() > 18;
                });
    System.out.println("All are adult? " + isAllAdult);

    boolean isAnyChild = users.stream().anyMatch(p -> p.getAge() < 12);
    System.out.println("Any Child? " + isAnyChild);

    boolean noneOldThan19 = users.stream().noneMatch(p -> p.getAge() > 19);
    System.out.println("none Old Than 19? " + noneOldThan19);

    boolean noneOldThan50 = users.stream().noneMatch(p -> p.getAge() > 50);
    System.out.println("none Old Than 50? " + noneOldThan50);
  }

  /** 在管道中，必须利用limit之类的操作限制Stream大小。 */
  @Test
  void generate() {
    Random seed = new Random();
    Supplier<Integer> random = seed::nextInt;
    Stream.generate(random).limit(10).forEach(System.out::println);
    // Another way
    IntStream.generate(() -> (int) (System.nanoTime() % 100))
        .limit(10)
        .forEach(System.out::println);
  }

  private static User getUser() {
    User user = new User();
    user.setAge(new Random().nextInt(100));
    return user;
  }

  /** 自实现Supplier 构造海量测试数据 */
  @Test
  void generate1() {
    List<User> collect =
        Stream.generate(StreamTest::getUser).limit(10).collect(Collectors.toList());

    Supplier<User> userSupplier =
        () -> {
          User user = new User();
          user.setAge(new Random().nextInt(100));
          return user;
        };

    Stream.generate(
            () -> {
              User user = new User();
              user.setAge(new Random().nextInt(100));
              return user;
            })
        .limit(100)
        .forEach(System.out::println);

    Stream.generate(new UserSupplier())
        .limit(100)
        .forEach(p -> System.out.println(p.getName() + ":" + p.getAge()));
  }

  @Test
  void sequence() {
    Stream.iterate(1, n -> n * 2).limit(10).forEach(x -> System.out.print(x + " "));
    System.out.println();
    Stream.iterate(4, n -> n + 3).limit(10).forEach(x -> System.out.print(x + " "));
  }

  @Test
  void toArray() {

    Stream<String> tokenStream = Arrays.asList("A", "B", "C", "D").stream(); // stream

    String[] tokenArray = tokenStream.toArray(String[]::new); // array

    System.out.println(Arrays.toString(tokenArray));

    IntStream infiniteNumberStream = IntStream.iterate(1, i -> i + 1);

    int[] intArray = infiniteNumberStream.limit(10).toArray();

    System.out.println(Arrays.toString(intArray));
  }

  @Test
  void groupByAge() {

    List<User> userList =
        Stream.generate(new UserSupplier()).limit(100).collect(Collectors.toList());
    Map<Integer, List<User>> collect =
        userList.stream().collect(Collectors.groupingBy(User::getAge));

    Map<Integer, List<User>> collect1 =
        userList.stream()
            .collect(Collectors.groupingBy(User::getAge, TreeMap::new, Collectors.toList()));

    TreeMap<Integer, List<User>> collect3 =
        userList.stream()
            .sorted(Comparator.comparingLong(User::getBalance))
            .collect(Collectors.groupingBy(User::getAge, TreeMap::new, Collectors.toList()));
    // collectingAndThen先把流中的所有元素传递给第二个参数，然后把生成的集合传递给第一个参数来处理。
    Stream<User> collect2 =
        userList.stream()
            .collect(
                Collectors.collectingAndThen(
                    Collectors.toList(),
                    item -> {
                      item.sort(Comparator.comparingLong(User::getBalance));
                      return item.stream();
                    }));

    Iterator it = collect.entrySet().iterator();
    while (it.hasNext()) {
      Map.Entry<Integer, List<User>> users = (Map.Entry) it.next();
      System.out.println("Age: " + users.getKey() + "=" + users.getValue().size());
    }
  }

  @Test
  void partitioningBy() {
    Map<Boolean, List<User>> children =
        Stream.generate(new UserSupplier())
            .limit(100)
            .collect(Collectors.partitioningBy(p -> p.getAge() > 18));
    System.out.println("Children number:" + children.get(false).size());
    System.out.println("Adult number:" + children.get(true).size());
  }

  @Test
  void parallel() {
    // 获取当前机器CPU处理器的数量
    System.out.println(Runtime.getRuntime().availableProcessors()); // 输出 4
    // parallelStream 默认的并发线程数
    System.out.println(ForkJoinPool.getCommonPoolParallelism()); // 输出 3

    IntStream intStream = IntStream.rangeClosed(1, 1000);
    // 第一个参数返回结果的容器，第二个参数流中的项对容器的操作，第三个参数是并行多个中间结果合并的操作。
    ArrayList<Integer> collect =
        intStream.parallel().collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    System.out.println(collect.size());
  }

  @Test
  void iterator() {
    // Create an array list for doubles.
    ArrayList<Integer> al = new ArrayList<>();

    // Add values to the array list.
    al.add(1);
    al.add(2);
    al.add(-3);
    al.add(-3);
    al.add(-4);
    al.add(5);

    Stream<Integer> str1 = al.stream();
    Spliterator<Integer> splitr1 = str1.spliterator();
    System.out.println(splitr1.characteristics());
    // 结合hasNext和next方法
    splitr1.tryAdvance((a) -> System.out.println(a + "!"));

    // trySplit()  将剩余元素分成两部分，取出第一部分
    Spliterator<Integer> splitr2 = splitr1.trySplit();
    // estimateSize 函数获取剩余还没有进行accept操作的元素的数量
    System.out.println(splitr2.estimateSize());

    Consumer<Integer> action = System.out::println;

    Consumer<Integer> hello =
        action.andThen(
            (a) -> {
              System.out.println("hello");
            });
    splitr2.forEachRemaining(hello);

    System.out.println();
    splitr1.forEachRemaining(action);
  }

  /** 并行迭代器 */
  @Test
  void iterator1() {
    int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9};
    // 第二个参数指向数组的起始下标（包括,0开始），第三个参数指向原数组的结束下标（不包括）
    Spliterator.OfInt sInt = Arrays.spliterator(arr, 2, 5);
    IntConsumer consumer = System.out::println;
    sInt.tryAdvance(consumer);
    sInt.tryAdvance(consumer);
    sInt.tryAdvance(consumer);
    sInt.tryAdvance(consumer);
    sInt.tryAdvance(consumer);
  }

  @Test
  void ttt() {
    int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    Spliterator<Integer> spliterator0 = Arrays.spliterator(arr);
    Spliterator<Integer> spliterator1 = spliterator0.trySplit();
    Spliterator<Integer> spliterator2 = spliterator1.trySplit();

    System.out.println(spliterator0.getExactSizeIfKnown());
    System.out.println(spliterator1.getExactSizeIfKnown());
    System.out.println(spliterator2.getExactSizeIfKnown());

    System.out.println(spliterator0.estimateSize());
    System.out.println(spliterator1.estimateSize());
    System.out.println(spliterator2.estimateSize());

    Thread t0 = new SplitIteratorThread<>(spliterator0);
    t0.setName("t0");

    Thread t1 = new SplitIteratorThread<>(spliterator1);
    t1.setName("t1");

    Thread t2 = new SplitIteratorThread<>(spliterator2);
    t2.setName("t2");

    t0.start();
    t1.start();
    t2.start();
  }

  /** 多层嵌套访问 */
  @Test
  void optionalTest() {
    User user = null;
    Optional.ofNullable(user).map(User::getRole).map(Role::getName).ifPresent(a -> a = "ADMIN");
  }
}
