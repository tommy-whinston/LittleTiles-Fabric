# Porting notes / Заметки о переносе

## English

This repository is a Fabric 1.20.1 port of the upstream LittleTiles `1.20` branch.

- Upstream: <https://github.com/CreativeMD/LittleTiles/tree/1.20>
- Audited upstream commit: `c5694e16070fd10460a093df817149128936ba86` (2026-05-08)
- Required library: [CreativeCore](https://github.com/CreativeMD/CreativeCore) `1.20` branch
- CreativeCore Fabric build used by this project: `2.12.41` for Minecraft 1.20.1
  (Modrinth version ID `3q9j0cNZ`)

### Current state

The Fabric project compiles against 1.20.1, starts on a dedicated server, declares CreativeCore
and contains the entrypoints for the actual port. The upstream LittleTiles source is imported on
the `port` branch and currently compiles with 0 errors; runtime launch is the next stage.

The project uses **Mojang official mappings** because the upstream `1.20` branch is written
against them. This avoids remapping the whole 77k-line source by hand and lets the port be
compared with upstream directly.

A temporary Forge compatibility layer is in place under:

- `src/main/java/net/minecraftforge/` — event bus bridge, `DeferredRegister`,
  `RegistryObject`, `ForgeRegistries`, FML shims, the event classes LittleTiles uses, and
  basic capability interfaces.
- `src/main/java/team/creative/creativecore/common/be/BlockEntityCreative.java` — Fabric
  replacement for the Forge-only CreativeCore base block entity.

It is compile-only for now. As the port progresses, the compatibility classes will be replaced
with native Fabric API calls and mixins.

### Known dependency gap

The published CreativeCore Fabric jar does not contain a few Forge-only classes that
LittleTiles uses. `BlockEntityCreative` has already been added to this project. The remaining
client-side classes are:

- `team.creative.creativecore.client.render.model.CreativeBlockModel`
- `team.creative.creativecore.client.render.model.CreativeBakedBoxModel`
- `team.creative.creativecore.client.render.model.CreativeItemModel`
- `team.creative.creativecore.client.render.model.CreativeItemBoxModel`
- `team.creative.creativecore.client.render.model.CreativeQuadLighter`
- `team.creative.creativecore.mixin.ForgeModelBlockRendererAccessor`

Internally those classes also need `CreativeBakedModel`, `CreativeUnbakedModel` and
`CreativeModelLoader`. Together this is roughly 600 lines of code. Port them into this project
before porting LittleTiles' client code.

### Suggested port order

1. **Registries.** Replace `DeferredRegister` / `RegistryObject` with Fabric registries.
   There are about 61 `RegistryObject` references and 17 `ForgeRegistries` references.
2. **Common data model.** Port the tile data, `BETiles` and the structure code. Most of this is
   vanilla API and should compile with few changes.
3. **Networking.** Keep CreativeCore's `CreativeNetwork`; LittleTiles does not use Forge's
   `SimpleChannel`.
4. **Events and capabilities.** Replace the 26 `@SubscribeEvent` handlers with Fabric API
   callbacks or mixins. Port `IItemHandler` / `IFluidHandler` / `LazyOptional` to the Fabric
   Transfer API or small adapters.
5. **Block entities and chunk data.** Port `BlockEntityCreative` and the chunk serializer hooks.
6. **Client rendering.** Port the mixins and the render pipeline. There are 67 mixins in
   upstream; 21 of them are Sodium/Embeddium compatibility.
7. **Models.** Rewrite the Forge model loader hooks (`ModelEvent.RegisterGeometryLoaders`,
   `ModelData`, `IClientBlockExtensions`) on Fabric's model loading API.
8. **Sodium.** Optional for the first playable build. Fabric Sodium 0.5 uses the same
   `me.jellysquid.mods.sodium` package as Embeddium, so much of the compatibility code can
   probably be reused; the Embeddium-only translucent sorter needs a separate solution.
9. **Testing.** Single-player, dedicated server, multiplayer, old worlds and the main tools.

### Useful numbers

- Upstream size: 656 Java files, ~77.6k lines.
- Forge imports: 432 lines across 177 files; 247 of those are `@OnlyIn` / `Dist` imports.
- 26 `@SubscribeEvent` handlers.
- 67 mixins: 21 Sodium/Embeddium, 46 other.
- No `SimpleChannel`, no `ForgeConfigSpec`.
- Capabilities: 11 `IItemHandler`, 6 `IFluidHandler`, 5 `LazyOptional` references.
- `ModelData`: 28 references.

---

## Русский

Этот репозиторий — Fabric-порт LittleTiles для Minecraft 1.20.1 на основе ветки `1.20`.

- Оригинал: <https://github.com/CreativeMD/LittleTiles/tree/1.20>
- Проверенный коммит оригинала: `c5694e16070fd10460a093df817149128936ba86` (2026-05-08)
- Обязательная библиотека: [CreativeCore](https://github.com/CreativeMD/CreativeCore), ветка `1.20`
- Используемая Fabric-сборка CreativeCore: `2.12.41` для Minecraft 1.20.1
  (ID версии на Modrinth: `3q9j0cNZ`)

### Текущее состояние

Проект собирается под Fabric 1.20.1, запускается на выделенном сервере, подключает CreativeCore
и содержит точки входа для будущего порта. Исходники LittleTiles импортированы в ветку `port`
и сейчас компилируются без ошибок; следующий этап — запуск в рантайме.

Проект использует **официальные маппинги Mojang**, потому что оригинальная ветка `1.20` написана
под них. Это избавляет от ручного переименования 77 тысяч строк и позволяет сравнивать порт
с оригиналом напрямую.

В проекте есть временный слой совместимости с Forge:

- `src/main/java/net/minecraftforge/` — шина событий, `DeferredRegister`, `RegistryObject`,
  `ForgeRegistries`, заглушки FML, события, которые использует LittleTiles, и базовые интерфейсы
  capabilities.
- `src/main/java/team/creative/creativecore/common/be/BlockEntityCreative.java` — Fabric-замена
  Forge-класса `BlockEntityCreative` из CreativeCore.

Пока слой нужен только для компиляции. По мере переноса классы совместимости будут заменяться
на обычные вызовы Fabric API и миксины.

### Чего не хватает в CreativeCore

В опубликованной Fabric-сборке CreativeCore нет нескольких Forge-классов, которые использует
LittleTiles. `BlockEntityCreative` уже добавлен в этот проект. Остались клиентские классы:

- `team.creative.creativecore.client.render.model.CreativeBlockModel`
- `team.creative.creativecore.client.render.model.CreativeBakedBoxModel`
- `team.creative.creativecore.client.render.model.CreativeItemModel`
- `team.creative.creativecore.client.render.model.CreativeItemBoxModel`
- `team.creative.creativecore.client.render.model.CreativeQuadLighter`
- `team.creative.creativecore.mixin.ForgeModelBlockRendererAccessor`

Внутри им также нужны `CreativeBakedModel`, `CreativeUnbakedModel` и `CreativeModelLoader`.
В сумме это примерно 600 строк. Их нужно перенести в проект до клиентского кода LittleTiles.

### Предлагаемый порядок переноса

1. **Реестры.** Заменить `DeferredRegister` / `RegistryObject` на реестры Fabric.
   В оригинале около 61 ссылки на `RegistryObject` и 17 на `ForgeRegistries`.
2. **Общая модель данных.** Перенести данные тайлов, `BETiles` и код структур. Большая часть
   использует обычный API Minecraft и должна собраться с небольшими правками.
3. **Сеть.** Оставить `CreativeNetwork` из CreativeCore; LittleTiles не использует Forge
   `SimpleChannel`.
4. **События и capabilities.** Заменить 26 обработчиков `@SubscribeEvent` на колбэки Fabric API
   или миксины. Перевести `IItemHandler` / `IFluidHandler` / `LazyOptional` на Fabric Transfer API
   или небольшие адаптеры.
5. **Block entities и данные чанков.** Перенести `BlockEntityCreative` и хуки сериализации чанков.
6. **Клиентский рендер.** Перенести миксины и конвейер рендера. В оригинале 67 миксинов,
   21 из них — совместимость с Sodium/Embeddium.
7. **Модели.** Переписать хуки загрузчика моделей Forge (`ModelEvent.RegisterGeometryLoaders`,
   `ModelData`, `IClientBlockExtensions`) на API загрузки моделей Fabric.
8. **Sodium.** Опционально для первой играбельной сборки. Fabric Sodium 0.5 использует тот же
   пакет `me.jellysquid.mods.sodium`, что и Embeddium, поэтому большую часть кода совместимости,
   вероятно, можно переиспользовать; для Embeddium-сортировщика прозрачных квадов нужно отдельное
   решение.
9. **Тестирование.** Одиночная игра, выделенный сервер, мультиплеер, старые миры и основные
   инструменты.

### Полезные цифры

- Размер оригинала: 656 Java-файлов, ~77,6 тыс. строк.
- Импорты Forge: 432 строки в 177 файлах; 247 из них — `@OnlyIn` / `Dist`.
- 26 обработчиков `@SubscribeEvent`.
- 67 миксинов: 21 Sodium/Embeddium, 46 остальных.
- Нет `SimpleChannel`, нет `ForgeConfigSpec`.
- Capabilities: 11 упоминаний `IItemHandler`, 6 `IFluidHandler`, 5 `LazyOptional`.
- `ModelData`: 28 упоминаний.
