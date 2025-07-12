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
@Warmup(iterations = 8, time = 1)
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

    String q1 = "/salary/?work_year=2025&salaryInUsdLt=100000&salaryInUsdGt=20000&pageSize=10";
    Integer[] ids1 = new Integer[]{136636, 39796, 40467, 61360, 30092, 5972, 11529, 12364, 31945, 40303};

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

    String q2 = "/salary/?jobTitle=Researcher&or.salaryInUsdGt=300000&or.salaryInUsdLt=30000&pageSize=10";
    Integer[] ids2 = new Integer[]{9488, 9487, 7599, 9003, 4949, 12564, 509, 10037, 13123, 8430};

    @Benchmark
    @Test
    public void dqQuery2() throws Exception {
        mockMvc.perform(get("/dq" + q2))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.list[*].id", hasItems(ids2)))
        ;
    }

    @Benchmark
    @Test
    public void jdbcQuery2() throws Exception {
        mockMvc.perform(get("/jdbc" + q2))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.list.size()").value(10))
                .andExpect(jsonPath("$.list[*].id", hasItems(ids2)))
        ;
    }

    @Benchmark
    @Test
    public void jpaQuery2() throws Exception {
        mockMvc.perform(get("/jpa" + q2))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.size()").value(10))
                .andExpect(jsonPath("$.content[*].id", hasItems(ids2)))
        ;
    }

    @Benchmark
    @Test
    public void jooqQuery2() throws Exception {
        mockMvc.perform(get("/jooq" + q2))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.list.size()").value(10))
                .andExpect(jsonPath("$.list[*].id", hasItems(ids2)))
        ;
    }

    String q3 = "/salary/?workYear=2025&salaryInUsdGt0.workYear=2023";
    Integer[] ids3 = new Integer[]{56675, 56676, 16197, 39564};

    @Benchmark
    @Test
    public void dqQuery3() throws Exception {
        mockMvc.perform(get("/dq" + q3))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.list[*].id", hasItems(ids3)))
        ;
    }

    @Benchmark
    @Test
    public void jdbcQuery3() throws Exception {
        mockMvc.perform(get("/jdbc" + q3))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.list[*].id", hasItems(ids3)))
        ;
    }

    @Benchmark
    @Test
    public void jpaQuery3() throws Exception {
        mockMvc.perform(get("/jpa" + q3))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[*].id", hasItems(ids3)))
        ;
    }

    @Benchmark
    @Test
    public void jooqQuery3() throws Exception {
        mockMvc.perform(get("/jooq" + q3))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.list[*].id", hasItems(ids3)))
        ;
    }
}
