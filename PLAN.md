# Development plan / План разработки

## English

Status markers: `[x]` done, `[~]` in progress, `[ ]` planned.

### Stage 0 - Repository and build foundation - `[x]` done

- Fabric 1.20.1, Loom, CreativeCore Fabric, entrypoints, CI.
- Attribution to CreativeMD/N247S and the AI-assisted notice in README and mod metadata.
- Mojang official mappings and the Forge compatibility layer.

**Done when:** `./gradlew build` succeeds and the mod starts on a dedicated server.

### Stage 1 - Import upstream source - `[x]` done on `port`

- Imported the LittleTiles 1.20 source, data files, assets and mixins.
- Optional integrations were disabled for the first pass (The One Probe, Chisel/ChiselsAndBits,
  Oculus).
- Sodium 0.5.11 is a compile-only dependency.

**Done when:** the upstream tree is in the repository.

### Stage 2 - Compile the common core - `[x]` done (with stubs)

- Replaced Forge-added vanilla methods with Fabric-friendly helpers/casts.
- Added Forge event/capability shims used by common code.
- Fixed `@Override` and signature differences.

**Done when:** common/server code compiles and `runServer` loads the registries.

### Stage 3 - Compile the client - `[x]` done (with stubs)

- Replaced Forge client events with Fabric callbacks/mixins.
- Added temporary CreativeCore client model classes.
- Fixed renderer registration and other client-only patches.

**Done when:** `compileJava` finishes with zero errors.

### Stage 4 - First real launch - `[x]` done

- Fabric entrypoints, registries, creative tab.
- Dedicated server reaches `Done`.
- Client launches, connects to the dedicated server and enters the world.

**Done when:** the mod loads in the dev client and on a dedicated server.

### Stage 5 - Tile rendering and interaction - `[~]` in progress

- [x] Tile blocks can be placed on the client and server.
- [x] `BETilesRenderer` renders tiles with cropped UVs instead of stretched block textures.
- [x] Full-block tiles use the vanilla block renderer, so their lighting matches normal blocks.
- [x] Placement preview is visible and shows the real structure blocks.
- [x] `multi_tiles` and `premade` items use a 3D item renderer for their inventory icons.
- [~] Face culling, translucent tiles, ambient occlusion and break particles.
- [ ] Remove temporary placement debug logging.
- [ ] Invalid placement should show a clear message instead of silently doing nothing.

**Done when:** a tile block can be placed, seen, broken and previewed correctly in normal play.

### Stage 6 - Server/client separation and stability - `[x]` done

- Audit common classes for direct references to client-only classes
  (`Minecraft`, `LocalPlayer`, `ClientLevel`, `SoundInstance`, `RenderBox`).
- Move client-only behavior into `client` packages or guard it with the Fabric environment.
- Fix server-side example/premade loading so no `Cannot load class ...` appears.
- Fix remaining CreativeCore config exceptions and data-fixer warnings.
- Add a clean dedicated-server smoke test that does not load client classes.

**Done when:** a dedicated server starts with no client-class errors, and structures/premades
initialize on both sides.

### Stage 7 - Structures and animation - `[~]` in progress

- [x] Structure registry, premade registry, example structures and recipes.
- [~] Place and use fixed structures, chairs, beds, storage, lights, item holders.
- [~] Doors and animated structures: state transitions, moving structures, sound.
- [x] Structure persistence, children connections and chunk reload.
- [~] Render animated structures through the correct render stages.

**Done when:** every built-in structure can be placed, used, saved and loaded.

### Stage 8 - Tools and GUIs - `[ ]` planned

- Hammer, chisel, saw, paint brush, glove, blueprint, screwdriver, wrench.
- Selection, placement modes, undo/redo, filters and configuration GUIs.
- Preview modes (previews, lines, marks) and tooltips.

**Done when:** every tool has its basic documented behavior in single-player.

### Stage 9 - Networking, save/load, old worlds - `[~]` in progress

- Tile block updates, action packets, structure packets, level initialization.
- Chunk data, block entity sync, reconnect persistence.
- Conversion of old LittleTiles data and the block conversion map.

**Done when:** two clients see the same tiles; a reconnect preserves tiles and structures.

### Stage 10 - Model, language and integration parity - `[ ]` planned

- Models and textures for every block/item (no magenta or missing models).
- English/Russian language coverage where the upstream project has it.
- Optional Sodium, The One Probe, Chisel/ChiselsAndBits and Oculus integrations.
- Performance pass for tile rendering and animation.

**Done when:** no missing model/texture warnings, and optional mods do not crash.

### Stage 11 - Release - `[ ]` planned

- Bilingual docs and status.
- CI green, reproducible build, release jar.
- Dedicated-server and single-player playtest.

**Done when:** a release jar works in normal play and on a dedicated server.

### Next ten tasks (in order)

1. Remove direct client-class references from common initialization.
2. Fix server example/premade loading and verify a clean dedicated-server log.
3. Remove temporary placement debug logging.
4. Finish the placement preview (valid/invalid feedback, no silent failures).
5. Implement tile face culling, translucency and ambient occlusion.
6. Finish 3D icons for every premade and example structure.
7. Verify static structures: chair, bed, storage, light, item holder.
8. Implement door/animation rendering and persistence.
9. Port tool/GUI behavior and preview modes.
10. Clean models, language files and optional integrations.

### Working rules

- The `1.20.1` branch must stay buildable and green.
- WIP work happens on `port`.
- Every stage ends with a build and a server/client smoke test when possible.
- No merge into `1.20.1` until the stage's "done when" condition is met.

---

## Русский

Статусы: `[x]` готово, `[~]` в работе, `[ ]` запланировано.

### Этап 0 - Репозиторий и сборка - `[x]` готово

- Fabric 1.20.1, Loom, CreativeCore Fabric, точки входа, CI.
- Атрибуция CreativeMD/N247S и пометка об AI-помощи в README и метаданных мода.
- Официальные маппинги Mojang и слой совместимости с Forge.

**Готово, когда:** `./gradlew build` проходит, а мод запускается на выделенном сервере.

### Этап 1 - Импорт исходников - `[x]` готово в `port`

- Импортированы исходники LittleTiles 1.20, данные, ресурсы и миксины.
- Опциональные интеграции отключены для первого прохода (The One Probe,
  Chisel/ChiselsAndBits, Oculus).
- Sodium 0.5.11 подключён только для компиляции.

**Готово, когда:** дерево upstream находится в репозитории.

### Этап 2 - Компиляция общего ядра - `[x]` готово (с заглушками)

- Forge-методы заменены на Fabric-совместимые помощники и приведения типов.
- Добавлены заглушки событий и capabilities, нужные общему коду.
- Исправлены `@Override` и различия сигнатур.

**Готово, когда:** общий/серверный код компилируется, а `runServer` загружает реестры.

### Этап 3 - Компиляция клиента - `[x]` готово (с заглушками)

- Forge-события клиента заменены на Fabric-колбэки и миксины.
- Добавлены временные клиентские классы моделей CreativeCore.
- Исправлена регистрация рендереров и другие клиентские патчи.

**Готово, когда:** `compileJava` завершается без ошибок.

### Этап 4 - Первый реальный запуск - `[x]` готово

- Точки входа Fabric, реестры, creative tab.
- Выделенный сервер доходит до `Done`.
- Клиент запускается, подключается к серверу и входит в мир.

**Готово, когда:** мод загружается в dev-клиенте и на выделенном сервере.

### Этап 5 - Рендер и взаимодействие с тайлами - `[~]` в работе

- [x] Блоки тайлов ставятся на клиенте и сервере.
- [x] `BETilesRenderer` рисует тайлы с обрезанными UV, а не растянутой текстурой.
- [x] Полные блоки тайлов используют ванильный рендер, поэтому освещение совпадает.
- [x] Превью установки видно, оно показывает реальные блоки структуры.
- [x] У `multi_tiles` и `premade` есть 3D-иконки структур в инвентаре.
- [~] Отсечение невидимых граней, прозрачные тайлы, ambient occlusion и частицы ломания.
- [ ] Убрать временные debug-логи установки.
- [ ] При неудачной установке показывать понятное сообщение, а не молчать.

**Готово, когда:** блок тайлов можно поставить, увидеть, сломать и увидеть корректное превью.

### Этап 6 - Разделение клиента и сервера, стабильность - `[x]` готово

- Проверить общие классы на прямые ссылки на клиентские классы
  (`Minecraft`, `LocalPlayer`, `ClientLevel`, `SoundInstance`, `RenderBox`).
- Вынести клиентское поведение в `client`-пакеты или закрыть проверкой окружения.
- Починить загрузку примеров и премейдов на сервере, чтобы не было
  `Cannot load class ...`.
- Убрать оставшиеся ошибки конфигурации CreativeCore и предупреждения data fixer.
- Добавить чистый smoke-test выделенного сервера без загрузки клиентских классов.

**Готово, когда:** сервер запускается без клиентских ошибок, а структуры и премейды
инициализируются с обеих сторон.

### Этап 7 - Структуры и анимации - `[~]` в работе

- [x] Реестр структур, реестр премейдов, примеры структур и рецепты.
- [ ] Установка и использование fixed-структур, стульев, кроватей, хранилищ, света,
  держателей предметов.
- [ ] Двери и анимированные структуры: переходы состояний, движущиеся структуры, звук.
- [ ] Сохранение структур, связи детей и перезагрузка чанков.
- [ ] Рендер анимированных структур через правильные стадии рендера.

**Готово, когда:** каждую встроенную структуру можно поставить, использовать, сохранить
и загрузить.

### Этап 8 - Инструменты и GUI - `[ ]` запланировано

- Молоток, долото, пила, кисть, перчатка, чертёж, отвёртка, гаечный ключ.
- Выделение, режимы установки, undo/redo, фильтры и конфигурационные GUI.
- Режимы превью (previews, lines, marks) и подсказки.

**Готово, когда:** каждый инструмент выполняет своё базовое документированное поведение
в одиночной игре.

### Этап 9 - Сеть, сохранение и старые миры - `[~]` в работе

- Обновления блоков тайлов, пакеты действий, пакеты структур, инициализация уровня.
- Данные чанков, синхронизация block entity, сохранение после переподключения.
- Конвертация старых данных LittleTiles и таблицы конвертации блоков.

**Готово, когда:** два клиента видят одинаковые тайлы, а переподключение сохраняет
тайлы и структуры.

### Этап 10 - Модели, язык и интеграции - `[ ]` запланировано

- Модели и текстуры для всех блоков и предметов (без magenta и missing model).
- Английский/русский язык там, где он есть в upstream.
- Опциональные интеграции Sodium, The One Probe, Chisel/ChiselsAndBits и Oculus.
- Оптимизация рендера тайлов и анимаций.

**Готово, когда:** нет предупреждений о missing model/texture, а опциональные моды
не вызывают крашей.

### Этап 11 - Релиз - `[ ]` запланировано

- Двуязычная документация и статус.
- Зелёный CI, воспроизводимая сборка, релизный jar.
- Тест на выделенном сервере и в одиночной игре.

**Готово, когда:** релизный jar работает в обычной игре и на выделенном сервере.

### Ближайшие десять задач (по порядку)

1. Убрать прямые ссылки на клиентские классы из общей инициализации.
2. Починить загрузку примеров и премейдов на сервере, проверить чистый лог.
3. Убрать временные debug-логи установки.
4. Доделать превью установки (валидное/невалидное, без молчаливых отказов).
5. Сделать отсечение граней, прозрачность и ambient occlusion для тайлов.
6. Доделать 3D-иконки для всех премейдов и примеров структур.
7. Проверить статические структуры: стул, кровать, хранилище, свет, держатель предмета.
8. Реализовать рендер и сохранение дверей и анимаций.
9. Портировать поведение инструментов, GUI и режимов превью.
10. Почистить модели, языковые файлы и опциональные интеграции.

### Правила работы

- Ветка `1.20.1` должна оставаться собираемой и зелёной.
- WIP-работа идёт в `port`.
- Каждый этап заканчивается сборкой и, когда возможно, smoke-тестом сервера/клиента.
- Не мержить в `1.20.1`, пока не выполнено условие "готово, когда" этапа.
