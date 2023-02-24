package me.luppolem.socksapp.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import me.luppolem.socksapp.model.Color;
import me.luppolem.socksapp.model.Size;
import me.luppolem.socksapp.model.Socks;
import me.luppolem.socksapp.services.SocksService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/socks")
@RequiredArgsConstructor
@Tag(name = "Склад носков", description = "CRUD-операции для работы на складе носков")
public class SocksController {
    private final SocksService socksService;


    @PostMapping
    @Operation(
            summary = "Приход носков на склад",
            description = "Носки добавляются на склад путем заполнения пяти полей json-файла"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Носки успешно оприходованы на складе",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Socks.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Параметры запроса отсутствуют или имеют некорректный формат"

            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Произошла ошибка, не зависящая от вызывающей стороны"

            )

    })
    public ResponseEntity<Socks> registerSocksInWarehouse(@Valid @RequestBody Socks socks) {

        return ResponseEntity.ok(socksService.addSocks(socks));
    }

//    @PutMapping
//    @Operation(
//            summary = "Отпуск носков со склада",
//            description = "Носки отпускаются со склада путем введения полей json-файла"
//    )
//    @ApiResponses(value = {
//            @ApiResponse(
//                    responseCode = "200",
//                    description = "Носки успешно отпущены сос склада",
//                    content = {
//                            @Content(
//                                    mediaType = "application/json",
//                                    schema = @Schema(implementation = Socks.class)
//                            )
//                    }
//            ),
//            @ApiResponse(
//                    responseCode = "400",
//                    description = "Товара нет на складе в нужном количестве или параметры запроса имеют некорректный формат"
//
//            ),
//            @ApiResponse(
//                    responseCode = "500",
//                    description = "Произошла ошибка, не зависящая от вызывающей стороны"
//
//            )
//    })
//    ResponseEntity<Object> releaseOfSocks(@Valid @RequestBody Socks socks) {
//        return ResponseEntity.ok(socksService.updateSocks(socks));
//    }

    @GetMapping
    @Operation(
            summary = "Получение информации о количестве носков по атрибутам: цвет, размер, " +
                    "минимальный и максимальный уровень хлопка",
            description = "Информацию о количестве носков можно получить путем ввода одного или нескольких " +
                    "атрибутов - цвета, размера - числового значение от 36.0 до 39.0 с шагом 0.5," +
                    "а также минимального и максимального уровня хлопка - целого числа от 0 до 100"
    )
    @Parameters(value = {
            @Parameter(
                    name = "color", example = "Белый"
            ),
            @Parameter(
                    name = "size", example = "36.5"
            ),
            @Parameter(
                    name = "cottonMin", example = "10"
            ),
            @Parameter(
                    name = "cottonMax", example = "90"
            ),
    })
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Информация о количестве носков по заданным параметрам получена"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Параметры запроса отсутствуют или имеют некорректный формат"

            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Произошла ошибка, не зависящая от вызывающей стороны"

            )
    })
    public Integer getQuantityOfSocks(@RequestParam(required = false) Color color,
                                      @RequestParam(required = false) Size size,
                                      @RequestParam(required = false) Integer cottonMin,
                                      @RequestParam(required = false) Integer cottonMax
    ) {

        return socksService.getQuantityOfSocks(color, size, cottonMin, cottonMax);
    }


}
