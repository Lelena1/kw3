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
import me.luppolem.socksapp.model.Warehouse;
import me.luppolem.socksapp.services.SocksService;
import me.luppolem.socksapp.services.WarehouseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/socks")
@RequiredArgsConstructor
@Tag(name = "Склад носков", description = "CRUD-операции для работы на складе носков")
public class WarehouseController {
    private final WarehouseService warehouseService;
    private final SocksService socksService;

    @PostMapping
    @Operation(
            summary = "Приход носков на склад",
            description = "Носки добавляются на склад путем заполнения четырех полей json-файла"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Носки успешно оприходованы на складе",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Warehouse.class))
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
    public ResponseEntity<Integer> registerSocksInWarehouse(@Valid @RequestBody Warehouse warehouse) {
        int id = warehouseService.addWarehouse(warehouse).getQuantity();
        return ResponseEntity.ok(id);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Отпуск носков со склада",
            description = "Носки отпускаются со склада путем введения идентификатора и одного или нескольких полей" +
                    "json-файла"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Рецепт изменен",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Warehouse.class)
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

    @Parameters(value = {
            @Parameter(name = "id",
                    example = "1")
    })
    ResponseEntity<Warehouse> updateRecipe(@PathVariable Integer id, @Valid @RequestBody Warehouse warehouse) {
        return ResponseEntity.ok(warehouseService.updateWarehouse(id, warehouse));
    }

    @GetMapping("{id}")
    @Operation(
            summary = "Получение информации о количестве носков по id в соответствии с параметрами: цвет, размер, " +
                    "минимальный и максимальный уровень хлопка",
            description = "Информацию о количестве носков, используя их id, можно получить путем ввода одного или нескольких " +
                    "параметров - цвета, размера - числового значение от 36.0 до 39.0 с шагом 0.5," +
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
                    name = "minCottonPart", example = "10"
            ),
            @Parameter(
                    name = "maxCottonPart", example = "90"
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
    public ResponseEntity<Warehouse> getWarehouseInfo(@PathVariable Integer id,
                                                      @RequestParam(required = false, name = color) Color color,
                                                      @RequestParam(required = false, name = size) Size size,
                                                      @RequestParam(required = false, name = minCottonPart) Integer minCottonPart,
                                                      @RequestParam(required = false, name = maxCottonPart) Integer maxCottonPart
    ) {
        Recipe recipe = recipesService.getRecipe(id);
        if (recipe == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recipe);
    }

    @GetMapping
    @Operation(
            summary = "Получение списка всех рецептов",
            @@-95, 9 + 102, 26@@public ResponseEntity<Collection<Recipe>>getAllRecipes(){
            }

            @GetMapping("/{all}")
}
