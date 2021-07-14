package br.com.zupacademy.enricco.mercadolivre.model;

import br.com.zupacademy.enricco.mercadolivre.controller.request.NewCharacteristic;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ProductTests {


    @DisplayName("Teste vai rejeitar com menos de 3 características")
    @ParameterizedTest
    @MethodSource("generatorForTest1")
    public void testWithLessThan3Characteristics1(Collection<NewCharacteristic> characteristicsList){
        Category category = new Category("Artigos esportivos",null);
        User user = new User("mjbetterthanlebron@email.com",new BCryptPasswordEncoder().encode("1234"));

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Product("Taco de golfe",1,"Metálico", BigDecimal.valueOf(1000),category,user,characteristicsList.stream().collect(Collectors.toList()))
                );

    }

    @DisplayName("Teste vai criar Produto com 3 ou mais características")
    @ParameterizedTest
    @MethodSource("generatorForTest2")
    public void testWithMoreThan3Characteristics2(Collection<NewCharacteristic> characteristicsList){
        Category category = new Category("Artigos esportivos",null);
        User user = new User("mjbetterthanlebron@email.com",new BCryptPasswordEncoder().encode("1234"));

        Product product = new Product("Taco de golfe",1,"Metálico", BigDecimal.valueOf(1000),category,user,characteristicsList.stream().collect(Collectors.toList()));


    }

    @DisplayName("Teste irá reduzir")
    @ParameterizedTest
    @MethodSource("generatorForTest2")
    public void testReduceQuantity3(Collection<NewCharacteristic> characteristicsList){
        Category category = new Category("Artigos esportivos",null);
        User user = new User("mjbetterthanlebron@email.com",new BCryptPasswordEncoder().encode("1234"));

        Product product = new Product("Taco de golfe",1,"Metálico", BigDecimal.valueOf(1000),category,user,characteristicsList.stream().collect(Collectors.toList()));
        boolean mustBeFalse = product.reduceStock(2);
        boolean mustBeTrue1 = product.reduceStock(1);
        boolean mustBeFalse1 = product.reduceStock(0);
        Assert.isTrue(mustBeTrue1,"Pode reduzir e chegar a 0");
        Assert.isTrue(!mustBeFalse,"Não pode reduzir mais q o limite");
        Assert.isTrue(!mustBeFalse1,"Não reduzir 0");
        Assert.isTrue(product.getQtd()==0,"Só vai reduzir 1");
       // Assertions.assertThrows(IllegalArgumentException.class,()->product.reduceStock(0),"Não pode reduzir 0");
    }

    //Test Generators
    public static Stream<Arguments> generatorForTest1(){
        return Stream.of(
                Arguments.of(
                        Set.of(
                                new NewCharacteristic("tamanho","imenso de grande"),
                                new NewCharacteristic("cheiro","incrível")
                        )
                ),
                Arguments.of(
                        Set.of(
                                new NewCharacteristic("Textura","aveludado")
                        )
                ),
                Arguments.of(
                        Set.of(
                        )
                )
        );
    }

    public static Stream<Arguments> generatorForTest2(){
        return Stream.of(
                Arguments.of(
                        Set.of(
                                new NewCharacteristic("tamanho","imenso de grande"),
                                new NewCharacteristic("cheiro","incrível"),
                                new NewCharacteristic("gosto","Tem um gosto meio roxo")
                        )
                ),
                Arguments.of(
                        Set.of(
                                new NewCharacteristic("Textura","aveludado"),
                                new NewCharacteristic("tamanho","imenso de grande"),
                                new NewCharacteristic("cheiro","incrível"),
                                new NewCharacteristic("gosto","Tem um gosto meio roxo")
                        )
                ),
                Arguments.of(
                        Set.of(
                                new NewCharacteristic("Textura","aveludado"),
                                new NewCharacteristic("tamanho","imenso de grande"),
                                new NewCharacteristic("cheiro","incrível"),
                                new NewCharacteristic("gosto","Tem um gosto meio roxo"),
                                new NewCharacteristic("Muito inteiressante","Louco demais")
                        )
                )
        );
    }

}
