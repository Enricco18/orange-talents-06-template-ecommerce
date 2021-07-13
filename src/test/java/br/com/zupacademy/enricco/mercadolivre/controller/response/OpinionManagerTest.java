package br.com.zupacademy.enricco.mercadolivre.controller.response;

import br.com.zupacademy.enricco.mercadolivre.controller.request.NewCharacteristic;
import br.com.zupacademy.enricco.mercadolivre.model.Category;
import br.com.zupacademy.enricco.mercadolivre.model.Opinion;
import br.com.zupacademy.enricco.mercadolivre.model.Product;
import br.com.zupacademy.enricco.mercadolivre.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

public class OpinionManagerTest {
    private static Category category;
    private static User user;
    private static Product product;

    @BeforeAll
    public static void init(){
        category = new Category("Artigos esportivos",null);
        user = new User("mjbetterthanlebron@email.com",new BCryptPasswordEncoder().encode("1234"));
        product = new Product("Taco de golfe",1,"Metálico", BigDecimal.valueOf(1000),category,user,
                List.of(
                        new NewCharacteristic("tamanho","imenso de grande"),
                        new NewCharacteristic("cheiro","incrível"),
                        new NewCharacteristic("gosto","Tem um gosto meio roxo")
                )
        );
    }

    @DisplayName("Teste de média e número de avaliações")
    @ParameterizedTest
    @MethodSource("generatorForTest1")
    public void opnionsCalculation1(List<Short> stars){
        List<Opinion> opinions = new ArrayList<>();
        AtomicReference<Integer> numberOfOpinions = new AtomicReference<>(0);
        AtomicReference<Float> total = new AtomicReference<>(0f);

        stars.forEach(star->{
            try {
                opinions.add(new Opinion(star,"Incrível","muito bom", user,product));
                numberOfOpinions.getAndSet(numberOfOpinions.get() + 1);
            }catch (Exception e){
                Assert.notNull(e,"Houve erro na criação do teste");
            }

        });


        opinions.stream().forEach(opinion -> {
            total.updateAndGet(v -> v + opinion.getStars());
        });

        OpinionManager opinionManager = new OpinionManager();
        opinionManager.getOpinionsData(opinions);
        int compare = Float.compare(total.get()/ numberOfOpinions.get(),opinionManager.getAverage_rating());

        Assert.isTrue(compare==0,"Os valores devem coincidir");
        Assert.isTrue(opinionManager.getNumber_of_ratings()== numberOfOpinions.get(),"O valor de opniões tem que ser igual!");

    }
    //Test Generators
    public static Stream<Arguments> generatorForTest1(){
        return Stream.of(
                Arguments.of(
                        List.of(
                                (short)  1,
                                (short)  1,
                                (short)  1
                        )
                ),
                Arguments.of(

                        List.of(
                                (short)  1,
                                (short)  2,
                                (short)  3
                        )
                ),
                Arguments.of(

                        List.of(
                                (short)  3,
                                (short)  2,
                                (short)  1
                        )
                ),
                Arguments.of(

                        List.of(
                                (short)  2,
                                (short)  3,
                                (short)  3
                        )
                )
        );
    }
}
