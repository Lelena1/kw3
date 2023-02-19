package me.luppolem.socksapp.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.luppolem.socksapp.model.Warehouse;
import me.luppolem.socksapp.services.SocksService;
import me.luppolem.socksapp.services.WarehouseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/socks")
@Tag(name = "Склад носков", description = "CRUD-операции для работы на складе носков")
public class WarehouseController {
private final SocksService socksService;
private final WarehouseService warehouseService;


    public WarehouseController(SocksService socksService, WarehouseService warehouseService) {
        this.socksService = socksService;
        this.warehouseService = warehouseService;
    }
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
                                    array = @ArraySchema(schema = @Schema(implementation = Warehouse.class))
                            )
                    }
            )
    })
    public ResponseEntity<Integer> registerSocksInWarehouse(@RequestBody Warehouse warehouse) {
        long id = warehouseService.addWarehouse(warehouse);
        return ResponseEntity.ok(id);
    }
    @GetMapping("/{id}")
    @Operation(
            summary = "Получение рецепта по идентификатору",
            description = "Рецепт можно получить путем ввода идентификатора - целого числа, большего либо равного 0"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Рецепт успешно получен",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Recipe.class))
                            )
                    }
            )
    })
    public ResponseEntity<Recipe> getRecipeById(@PathVariable long id) {
        Recipe recipe = recipesService.getRecipe(id);
        if (recipe == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recipe);
    }

    @GetMapping
    @Operation(
            summary = "Получение списка всех рецептов",
            @@ -95,9 +102,26 @@ public ResponseEntity<Collection<Recipe>> getAllRecipes() {
            }

            @GetMapping("/{all}")
}
