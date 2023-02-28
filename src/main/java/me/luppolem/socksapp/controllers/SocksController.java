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
            description = "Носки добавляются на склад путем заполнения полей json-файла"
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
    public void registerSocksInWarehouse(@Valid @RequestBody Socks socks) {

        socksService.addSocks(socks.getColor(), socks.getSize(), socks.getCottonPart(),
                socks.getQuantity());
    }

    @PutMapping
    @Operation(
            summary = "Отпуск носков со склада",
            description = "Носки отпускаются со склада путем введения полей json-файла"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Носки успешно отпущены со склада",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Socks.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Товара нет на складе в нужном количестве или параметры запроса имеют некорректный формат"

            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Произошла ошибка, не зависящая от вызывающей стороны"

            )
    })
    ResponseEntity<Object> releaseOfSocks(@Valid @RequestBody Socks socks) {
        return ResponseEntity.ok(socksService.updateSocks(socks.getColor(), socks.getSize(), socks.getCottonPart(), socks.getQuantity()));
    }

    @GetMapping
    @Operation(
            summary = "Получение информации о количестве носков по атрибутам: цвет, размер, " +
                    "минимальный и максимальный уровень хлопка",
            description = "Информацию о количестве носков можно получить путем ввода одного или нескольких " +
                    "атрибутов - цвета, размера, а также минимального и максимального уровня хлопка - целого числа от 0 до 100"
    )
    @Parameters(value = {
            @Parameter(
                    name = "color", example = "Белый"
            ),
            @Parameter(
                    name = "size", example = "L"
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
    public Integer getQuantityOfSocks(@RequestParam(required = true) Color color,
                                      @RequestParam(required = true) Size size,
                                      @RequestParam(required = true) Integer cottonMin,
                                      @RequestParam(required = true) Integer cottonMax
    ) {

        return socksService.getQuantityOfSocks(color, size, cottonMin, cottonMax);
    }

    @DeleteMapping
    @Operation(
            summary = "Регистрация списания бракованных носков",
            description = "Брак списывается со склада путем введения полей json-файла носков"
    )

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Запрос выполнен. Брак успешно списан со склада",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Socks.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "параметры запроса отсутствуют или имеют некорректный формат"

            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Произошла ошибка, не зависящая от вызывающей стороны"

            )
    })
    public boolean deleteDefectiveSocks(@Valid @RequestBody Socks socks) {
        socksService.removeDefectiveSocks(socks.getColor(), socks.getSize(), socks.getCottonPart(), socks.getQuantity());
        return true;
    }


}
