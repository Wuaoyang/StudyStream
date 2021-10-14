package site.wayblog.com.stream;

import org.junit.Test;
import site.wayblog.com.stream.entity.Employee;

import java.util.*;
import java.util.stream.*;
import java.util.stream.Stream;

/**
 * 跟随Stream类的步伐
 * 一个一个API过一遍
 * 学习的话，建议你也跟着敲一遍 ^ _ ^
 *
 * @author aidem
 * @date 2021-10-13 21:42
 */
public class StreamFullApi {

	// ====================================== Test域  ======================================

	// 1. 创建Stream
	@Test
	public void testCtreatStream() {
		List<Employee> list = getList();
		// 1. 普通串行流
		Stream<Employee> stream = list.stream();
		// todo 并行流 基于 fork-join 框架，fork-join是啥？
		// 2. 并行流
		Stream<Employee> parallelStream = list.parallelStream();
		// todo 对于 int long double，有特殊返回类型 IntStream, LongStream, DoubleStream 和 普通Stream<T>有啥区别？
		// 3. 数组创建流
		IntStream arrayInt = Arrays.stream(new int[] { 1, 2, 3 });
		LongStream arrayLong = Arrays.stream(new long[] { 1L, 2L, 3L });
		DoubleStream arrayDouble = Arrays.stream(new double[] { 1.1, 2.2, 33 });
        Stream<String> StringStream = Arrays.stream(new String[]{"1", "2"});
        // 4. 由值创建流
        // 4.1 无限流
		// 注：iterate方法两个参数，第一个参数代表种子（起始值），第二个参数代表生成规则
		//		limit forEach属于下方操作，不属于创建流操作，不懂可以往下继续走。或者对照Stream类看
        Stream<Integer> iterateStream = Stream.iterate(0, o -> o + 1);
        // 使用例子
		iterateStream.limit(10).forEach(System.out::println);
		// 4.2 提供了一些工具生成流
		// 注：generate里使用到Lamdba类提及的Supplier接口，()->{return xx;}的形式，Math::random是简写
		Stream<Double> generate = Stream.generate(Math::random);
	}

	// 2. Stream中间操作
	@Test
	public void testMiddleStream() {
		List<Employee> list = getList();
		// 开始之前，对于繁杂的API，思路不清晰的情况下实在不想怼，换个思路贯穿，这样便于记忆。
		// 我们知道Stream出来的一个重大意义就是能做出SQL能做的事情
		// 那么，随便来几个非join结构的SQL，然后我们用Stream秒杀他
		// select * from table
		// ==> 注: 这好像不用stream... list就是了 跳过 废话文学开启.....

		// select name from table
		list.stream().map(Employee::getName).forEach(System.out::println);
		list.stream().map(o -> o.getName() + "concat concat !!!!").forEach(System.out::println);
		// ==> 注: map这个api，像是对流中元素逐个进行了遍历，然后根据我们的要求对流中元素进行了加强或者说是改造
		//   （但是必须有返回值，因为他参数是Fucntion接口），每个元素经过这种加强改造后会变成一个新的元素，这就是map，映射

		// select name from table where age > 20
		list.stream().filter(o -> o.getAge() > 20).map(Employee::getName).forEach(System.out::println);
		// ==> 注: 对比上面，使用到filter，在这里可以做sql中的where操作

		// select name from table where age > 20 limit 2
		list.stream().filter(o -> o.getAge() > 20).map(Employee::getName).limit(2).forEach(System.out::println);
		list.stream().filter(o -> {
			System.out.println("处理中:" + o.getName());
			return o.getAge() > 20;
		}).map(Employee::getName).limit(2).forEach(System.out::println);
		// ==> 注: 注意，第二个demo是为了看执行顺序加入的

		// select concat(name) from table where age > 20
		// todo flatMap参数定义的泛型看不大懂，需要查下
		list.stream().filter(o -> o.getAge() > 20).flatMap(o->filterCharacter(o.getName())).forEach(System.out::println);
		// ==> 注: 这个其实和map很像，就是把组成的新流，拼凑起来而已
		//  但是属实有点绕，可以理解成他是需要先处理成一组Stream<T>，然后外层是和map一样的Stream。也就要求我们flatMap里面的必须每个元素返回的都是Stream<T>

		// select name from table order by age asc
		list.stream().sorted((x,y)->x.getAge() - y.getAge()).forEach(System.out::println);
//		list.stream().sorted(Comparator.comparingInt(Employee::getAge)).forEach(System.out::println);
		// ==> 注: 看下sorted方法就知道咋写了，还好

		// select distinct name from table
		list.stream().map(Employee::getName).distinct().forEach(System.out::println);
		// ==> 注: 去重
	}

	// 3. Stream终止操作
	@Test
	public void testLastStream() {
		List<Employee> list = getList();
		// testMiddleStream例子中，会发现最终结尾的方法都是forEach，是的，这就是一个终止操作！
		// 再次声明，一个流只能执行一次终止操作！
		// 那么本方法将开始测试常用的终止操作.
		// 老规矩，上需求（不上SQL了，因为Stream终究是为了解决代码里繁杂的处理，终止操作不是SQL能完全做到的），然后解决，再注解

		// select count(1) from table
		System.out.println(list.stream().count());
		// ==> 注: 脱裤子放屁！啥都没操作 = list.size()，不过，这只是个demo ^_^...

		// 判断整个stream是否满足某个条件
		System.out.println("是否全部年龄大于10:" + list.stream().allMatch(o -> o.getAge() > 10));
		// ==> 注: 很容易看懂

		// 判断整个stream是否有满足某个条件的数据
		System.out.println("是否存在年龄大于10:" + list.stream().anyMatch(o -> o.getAge() > 10));
		// ==> 注: 同上

		// 判断整个stream是否没有一条满足某个条件
		System.out.println("是否不存在年龄大于10:" + list.stream().noneMatch(o -> o.getAge() > 10));
		// ==> 注: 同上

		// 拿出strema的第一个元素
		System.out.println(list.stream().findFirst());
		// ==> 注: 返回的是Optional，是一个防止空指针的东西，但在我看来没啥用...不深入讲
		//     Optional对象取值就是直接 optional.get() 可以在get之前判空等

		// 拿出stream的随机一个元素
		System.out.println(list.stream().findAny());
		// ==> 注: 返回的是Optional

		// 将list中所有age进行累加
		Optional<Integer> reduce = list.stream().map(Employee::getAge).reduce((x, y) -> x + y);
		System.out.println(reduce.isPresent() ? reduce.get() : "");

		// 取出list中最大age
//		list.stream().max((x,y)->{x.getAge() - y.getAge()})
		System.out.println(list.stream().max(Comparator.comparingInt(Employee::getAge)).get());
		// ==> 注: 返回的是Optional，用的时候要如131行，这里比较随意。

		// 取出list中最小age
		System.out.println(list.stream().min(Comparator.comparingInt(Employee::getAge)).get());
		// ==> 注: 同上

		// 将list中name提取出来，拼成一串，不使用flatmap
		System.out.println(list.stream().map(Employee::getName).reduce((x, y) -> x + "|" + y).get());

		// 将list中name提取出来，组成List<String>
		List<String> collect = list.stream().map(Employee::getName).collect(Collectors.toList());
		Set<String> set = list.stream().map(Employee::getName).collect(Collectors.toSet());
		Set<String> hashset = list.stream().map(Employee::getName).collect(Collectors.toCollection(HashSet::new));
		// ==> 注: 这是一个非常有趣的操作，当需求是提取某个对象list某个属性，组成list的时候，这个能派上用场！

		// 对list根据name分组，对salary进行合并，组成一个map<String name, Integer salarySum>
		Map<String, List<Employee>> groupMap = list.stream().collect(Collectors.groupingBy(Employee::getName));
		System.out.println(groupMap);
		// ==> 注: 直觉告诉我，这在一些场合也很有用..

		// 本来枚举的场景差不多了，但是如果有树级结构，分组内再分组呢？可以看到Collectors.groupingBy这个api里有多参数，应该能操作，试试？
		// 先根据名称分组，再根据年龄分组
		Map<String, Map<Integer, List<Employee>>> group2Map = list.stream().collect(Collectors.groupingBy(Employee::getName, Collectors.groupingBy(Employee::getAge)));
		System.out.println(group2Map);
		// ==> 注: 直觉告诉我，这属实有点小恶心，但应该也有妙用..demo保留把..

		// 恭喜通关!!!
	}
	// ====================================== Private  ======================================

	/**
	 * 返回实验List
	 */
	private List<Employee> getList() {
		List<Employee> emps = Arrays.asList(
				new Employee(102, "李四", 59, 6666.66),
				new Employee(101, "张三", 18, 9999.99),
				new Employee(103, "王五", 28, 3333.33),
				new Employee(104, "赵六", 8, 7777.77),
				new Employee(104, "赵六", 8, 7777.77),
				new Employee(104, "赵六", 8, 7777.77),
				new Employee(105, "田七", 38, 5555.55));
		return emps;
	}

	/**
	 * 将字符串转成单个字符的流集合 Stream<Character>
	 */
	private static Stream<Character> filterCharacter(String str) {
		List<Character> list = new ArrayList<>();
		for (Character ch : str.toCharArray()) {
			list.add(ch);
		}
		return list.stream();
	}


}
