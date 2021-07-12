package br.com.zupacademy.enricco.mercadolivre.model;

import br.com.zupacademy.enricco.mercadolivre.controller.request.NewCharacteristic;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OpnionTest {

    @DisplayName("Não pode criar Opinião com menos de 1 estrela e mais de 5")
    @ParameterizedTest
    @MethodSource("generatorForTest1")
    public void starsBoundarys1(Short stars){
        Category category = new Category("Artigos esportivos",null);
        User user = new User("mjbetterthanlebron@email.com",new BCryptPasswordEncoder().encode("1234"));
        Product product = new Product("Taco de golfe",1,"Metálico", BigDecimal.valueOf(1000),category,user,
                List.of(
                        new NewCharacteristic("tamanho","imenso de grande"),
                        new NewCharacteristic("cheiro","incrível"),
                        new NewCharacteristic("gosto","Tem um gosto meio roxo")
                )
        );

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Opinion(stars,"Incrível","muito bom", user,product)
        );

    }
    //Test Generators
    public static Stream<Arguments> generatorForTest1(){
        return Stream.of(
                Arguments.of(
                        (short)  0
                ),
                Arguments.of(
                        (short) 6
                )
        );
    }
}
