package site.wayblog.com.stream;

/**
 * 学习Stream 一个类带你吃透
 *
 * 使用Stream API 对集合数据进行操作，就类似于使用 SQL 执行的数
 * 据库查询。也可以使用 Stream API 来并行执行操作。简而言之，
 * Stream API 提供了一种高效且易于使用的处理数据的方式。
 *
 * @author aidem
 * @date 2021-10-12 22:03
 */
public class Stream {

	// ===================================== 须知 =======================================

	// 什么是Stream
	// ①Stream 自己不会存储元素。
	// ②Stream 不会改变源对象。相反，他们会返回一个持有结果的新Stream
	// ③Stream 操作是延迟执行的。这意味着他们会等到需要结果的时候才执行。

	// Stream 的操作三个步骤
	// ①创建Stream流。
	// ②对Stream流做中间操作。
	// ③对Stream流做终止操作。（一个流只能做一次终止操作）

	// ************************ 1.创建流  ************************

	// 1. 集合创建
	// Collection.stream() 返回一个顺序流；
	// Collection.parallelStream() 返回一个并行流；
	// 2. 由数组创建流
	// Arrays.stream(...)
	// 3.由值创建流
	// Stream.iterate() / Stream.generate()


	// ************************ 2.Stream 的中间操作 ************************

	// 1. 筛选与切片
	// filter(Predicate p)  接收 Lambda ， 从流中排除某些元素。（可以理解成，Sql中的where块）
	// distinct()   筛选，通过流所生成元素的 hashCode() 和 equals() 去除重复元素（Sql中的distinct）
	// limit(long maxSize)  截断流，使其元素不超过给定数量。(Sql中的limit)
	// skip(long n)   跳过元素，返回一个扔掉了前 n 个元素的流。若流中元素不足 n 个，则返回一个空流。与 limit(n) 互补（Sql中的where rownum > n）
	// 2. 映射
	// map(Function f)  接收一个函数作为参数，该函数会被应用到每个元素上，并将其映射成一个新的元素(抽出来一个key，value的对象集合)
	// mapToDouble(ToDoubleFunction f)  接收一个函数作为参数，该函数会被应用到每个元素上，产生一个新的 DoubleStream。
	// mapToInt(ToIntFunction f) 接收一个函数作为参数，该函数会被应用到每个元素上，产生一个新的 IntStream。
	// mapToLong(ToLongFunction f)  接收一个函数作为参数，该函数会被应用到每个元素上，产生一个新的 LongStream。
	// flatMap(Function f)  接收一个函数作为参数，将流中的每个值都换成另一个流，然后把所有流连接成一个流
	// 3. 排序
	// sorted()   产生一个新流，其中按自然顺序排序（Sql中的Order By）
	// sorted(Comparator comp) 产生一个新流，其中按比较器顺序排序(Sql中做了比较复杂的Order By)


	// ************************ 3.Stream 的终止操作 ************************
	// 1.查找与匹配
	// allMatch(Predicate p)  检查是否匹配所有元素（Sql中类似 select count(1) from table where ....，判断是否 = 不加where的count）
	// anyMatch(Predicate p)  检查是否至少匹配一个元素 （Sql中类似 select count(1) from table where ....，判断是否 > 0）
	// noneMatch(Predicate p) 检查是否没有匹配所有元素（Sql中类似 select count(1) from table where ....，判断是否 = 0）
	// findFirst() 返回第一个元素(Sql中类似 select * from table where rownum = 1)
	// findAny() 返回当前流中的任意元素 （Sql中类似 select any(xxxx) from table where ...）
	// count() 返回流中元素总数（同Sql count）
	// max(Comparator c) 返回流中最大值 (同Sql max)
	// min(Comparator c)  返回流中最小值（同Sql min）
	// forEach(Consumer c)  内部迭代(使用 Collection 接口需要用户去做迭代，称为外部迭代。相反，Stream API 使用内部迭代——它帮你把迭代做了)
	// 2. 归约
	// reduce(T iden, BinaryOperator b)  可以将流中元素反复结合起来，得到一个值。返回 T
	// reduce(BinaryOperator b)  可以将流中元素反复结合起来，得到一个值。 返回 Optional<T>
	// 备注：map 和 reduce 的连接通常称为map-reduce 模式，因 Google 用它来进行网络搜索而出名。
	// 3. 收集
	// collect(Collector c) 将流转换为其他形式。接收一个 Collector接口的实现，用于给Stream中元素做汇总的方法


}
