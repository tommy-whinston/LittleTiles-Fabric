# Development plan / План разработки

## English

Status markers: `[x]` done, `[~]` in progress, `[ ]` planned.

### Stage 0 — Repository and build foundation — `[x]` done

- Fabric 1.20.1, Loom, CreativeCore Fabric, entrypoints, CI.
- Attribution and AI notice in the README and mod metadata.
- Mojang official mappings and the first Forge compatibility layer.

**Done when:** `./gradlew build` succeeds and the mod starts on a dedicated server.

### Stage 1 — Import the upstream source — `[x]` done on `port`

- Imported the LittleTiles `1.20` source (618 main Java files, 158 data files, assets, mixins).
- Removed optional integrations for the first pass: The One Probe, Chisel/ChiselsAndBits, Oculus.
- Added Sodium 0.5.11 as a compile-only dependency.

**Done when:** the upstream tree is in the repository. The `port` branch does not compile yet.

### Stage 2 — Compile the common core — `[x]` done (with stubs)

Scope: registries, blocks/items, block entities, tile data, structures, networking, chunk data.

1. Replace Forge-added vanilla methods with Fabric-compatible helpers/casts
   (`getBlockEntity(pos, type)`, `getModelDataManager()`, `getFriction`, `getLightEmission`).
2. Finish the Forge event and capability shims needed by common code.
3. Fix `@Override` failures caused by Forge-only methods.
4. Get the common/server half of `compileJava` green.

**Done when:** common/server code compiles and `runServer` loads the registries without crashing.
This is the largest stage.

### Stage 3 — Compile the client — `[x]` done (with stubs)

- Replace Forge client events (`RenderLevelStageEvent`, `RenderGuiEvent`, `ModelEvent`, key
  mappings, overlays) with Fabric callbacks/mixins.
- Port the missing CreativeCore client model classes (`CreativeBlockModel`, `CreativeBakedBoxModel`,
  `CreativeItemModel`, `CreativeItemBoxModel`, `CreativeQuadLighter`).
- Fix `EntityRenderers.register` access and other client-only Forge patches.

**Done when:** `compileJava` finishes with zero errors.

### Stage 4 — First real launch — `[~]` in progress

- Adapt the upstream `LittleTiles` class to Fabric `ModInitializer` / `ClientModInitializer`.
- Register blocks, items, block entities and the creative tab.
- Make sure the client reaches the main menu and can create a world.

**Done when:** the mod loads in the dev client and on a dedicated server without a crash.

The dedicated server now reaches `Done`. Client launch and rendering remain.

### Stage 5 — Basic gameplay — `[ ]` planned

- Place/break the tile block.
- Render tiles in the world.
- Hammer/chisel interaction and collision.

**Done when:** a tile block can be placed, broken and seen in-game. This is the first visible
milestone.

### Stage 6 — Feature parity — `[ ]` planned

- Structures, doors, beds, storage, recipes, GUIs, packets, animations.
- Tools and preview rendering.

**Done when:** the main LittleTiles tools and structures work in single-player.

### Stage 7 — Stability and release — `[ ]` planned

- Sodium compatibility, multiplayer, old worlds, performance.
- Final testing and release jar.

**Done when:** no critical crashes in normal play and on a dedicated server.

### Next five tasks

1. Port the missing CreativeCore client model classes.
2. Fix generic `getBlockEntity` calls in common code.
3. Fix `EntityRenderers.register` access with an accessor mixin.
4. Add client event stubs/replacements to reduce the remaining error list.
5. Get the `port` branch to a green `compileJava`, then adapt the entrypoint.

### Working rules

- The `1.20.1` branch must stay buildable and green.
- WIP work happens on `port`.
- Every stage ends with a build and, where possible, a server/client start.
- No merge into `1.20.1` until the stage's “done when” condition is met.

---

## Русский

Статусы: `[x]` готово, `[~]` в работе, `[ ]` запланировано.

### Этап 0 — Репозиторий и фундамент сборки — `[x]` готово

- Fabric 1.20.1, Loom, CreativeCore Fabric, точки входа, CI.
- Указание автора оригинала и пометка об ИИ в README и метаданных мода.
- Официальные маппинги Mojang и первый слой совместимости с Forge.

**Готово, когда:** `./gradlew build` проходит, а мод запускается на выделенном сервере.

### Этап 1 — Импорт исходников — `[x]` готово в ветке `port`

- Импортированы исходники LittleTiles `1.20` (618 основных Java-файлов, 158 файлов данных,
  ресурсы, миксины).
- Для первого прохода убраны опциональные интеграции: The One Probe, Chisel/ChiselsAndBits,
  Oculus.
- Sodium 0.5.11 подключён как compile-only зависимость.

**Готово, когда:** дерево исходников лежит в репозитории. Ветка `port` пока не собирается.

### Этап 2 — Компиляция общего ядра — `[x]` готово (с заглушками)

Область: реестры, блоки/предметы, block entities, данные тайлов, структуры, сеть, данные чанков.

1. Заменить методы, которые Forge добавляет в ванильные классы, на Fabric-совместимые
   хелперы/касты (`getBlockEntity(pos, type)`, `getModelDataManager()`, `getFriction`,
   `getLightEmission`).
2. Доделать заглушки событий и capabilities, нужные общему коду.
3. Исправить ошибки `@Override` из-за методов, которых нет в ваниле.
4. Добиться компиляции общей/серверной части `compileJava`.

**Готово, когда:** общий и серверный код компилируется, а `runServer` загружает реестры без
краша. Это самый большой этап.

### Этап 3 — Компиляция клиента — `[x]` готово (с заглушками)

- Заменить клиентские события Forge (`RenderLevelStageEvent`, `RenderGuiEvent`, `ModelEvent`,
  кейбинды, оверлеи) на колбэки/миксины Fabric.
- Перенести недостающие клиентские модельные классы CreativeCore (`CreativeBlockModel`,
  `CreativeBakedBoxModel`, `CreativeItemModel`, `CreativeItemBoxModel`, `CreativeQuadLighter`).
- Исправить доступ к `EntityRenderers.register` и другие клиентские патчи Forge.

**Готово, когда:** `compileJava` завершается без ошибок.

### Этап 4 — Первый настоящий запуск — `[~]` в работе

- Адаптировать класс `LittleTiles` из оригинала под Fabric `ModInitializer` /
  `ClientModInitializer`.
- Зарегистрировать блоки, предметы, block entities и creative tab.
- Убедиться, что клиент доходит до главного меню и создаёт мир.

**Готово, когда:** мод загружается в dev-клиенте и на выделенном сервере без краша.

Выделенный сервер уже доходит до `Done`. Остались запуск клиента и рендер.

### Этап 5 — Базовая играбельность — `[ ]` запланировано

- Постановка и ломание блока тайлов.
- Рендер тайлов в мире.
- Взаимодействие молотком/стамеской и коллизии.

**Готово, когда:** блок тайлов можно поставить, сломать и увидеть в игре. Это первый видимый
результат.

### Этап 6 — Паритет функций — `[ ]` запланировано

- Структуры, двери, кровати, хранилища, рецепты, GUI, пакеты, анимации.
- Инструменты и превью.

**Готово, когда:** основные инструменты и структуры LittleTiles работают в одиночной игре.

### Этап 7 — Стабильность и релиз — `[ ]` запланировано

- Совместимость с Sodium, мультиплеер, старые миры, производительность.
- Финальное тестирование и релизный jar.

**Готово, когда:** нет критических крашей в обычной игре и на выделенном сервере.

### Ближайшие пять задач

1. Перенести недостающие клиентские модельные классы CreativeCore.
2. Исправить generic-вызовы `getBlockEntity` в общем коде.
3. Исправить доступ к `EntityRenderers.register` через accessor-миксин.
4. Добавить заглушки/замены клиентских событий, чтобы сократить список ошибок.
5. Добиться зелёного `compileJava` в ветке `port`, затем адаптировать точку входа.

### Правила работы

- Ветка `1.20.1` всегда должна собираться и оставаться зелёной.
- Рабочие изменения идут в ветку `port`.
- Каждый этап заканчивается сборкой и, где возможно, запуском сервера/клиента.
- Не мержить в `1.20.1`, пока не выполнено условие «готово, когда» этого этапа.
