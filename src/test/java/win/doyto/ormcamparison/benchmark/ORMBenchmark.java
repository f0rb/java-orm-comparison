package win.doyto.ormcamparison.benchmark;

import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import win.doyto.ormcamparison.ORMApplication;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.Matchers.hasItems;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * ORMBenchmark
 *
 * @author f0rb on 2024/8/9
 */
@ActiveProfiles("mysql8")
@AutoConfigureMockMvc
@SpringBootTest(classes = ORMApplication.class)

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@State(Scope.Benchmark)
@Fork(value = 1, jvmArgs = {"-Xms256M", "-Xmx2G", "-XX:+UseG1GC"})
@Threads(8)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 3, time = 3)
public class ORMBenchmark {

    public static void main(String[] args) throws Exception {
        Options opt = new OptionsBuilder().include(Benchmark.class.getSimpleName()).build();
        new Runner(opt).run();
    }

    private ConfigurableApplicationContext context;
    @Autowired
    private MockMvc mockMvc;

    @Setup
    public void init() {
        context = SpringApplication.run(ORMApplication.class, "--spring.profiles.active=mysql8");
        mockMvc = MockMvcBuilders.webAppContextSetup((WebApplicationContext) context).build();
    }

    @TearDown
    public void down() {
        context.close();
    }

    String q1 = "/salary/?salaryLt=100000&salaryCurrency=USD&pageSize=10";
    Integer[] ids1 = new Integer[]{131024, 133235, 135863, 136160, 52952, 52953, 52954, 52955, 52956, 52957};

    @Benchmark
    @Test
    public void dqQuery1() throws Exception {
        mockMvc.perform(get("/dq" + q1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.list.size()").value(10))
                .andExpect(jsonPath("$.list[*].id", hasItems(ids1)))
        ;
    }

    @Benchmark
    @Test
    public void jpaQuery1() throws Exception {
        mockMvc.perform(get("/jpa" + q1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.size()").value(10))
                .andExpect(jsonPath("$.content[*].id", hasItems(ids1)))
        ;
    }

    @Benchmark
    @Test
    public void jdbcQuery1() throws Exception {
        mockMvc.perform(get("/jdbc" + q1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.list.size()").value(10))
                .andExpect(jsonPath("$.list[*].id", hasItems(ids1)))
        ;
    }

    @Benchmark
    @Test
    public void jooqQuery1() throws Exception {
        mockMvc.perform(get("/jooq" + q1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.list.size()").value(10))
                .andExpect(jsonPath("$.list[*].id", hasItems(ids1)))
        ;
    }

//    String q2 = "/salary/?salaryInUsdLt=200000&salaryInUsdGt=100000&companySize=M&pageSize=10";


}
