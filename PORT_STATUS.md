# Port status / Статус порта

## English

This is the **work-in-progress port branch**. The default `1.20.1` branch contains the working
base; this branch contains the imported upstream source and the Fabric port.

- Upstream source: LittleTiles `1.20`, commit `c5694e16070fd10460a093df817149128936ba86`.
- Imported: 618 main Java files, 158 data files, assets and the upstream mixin configuration.
- **Build:** `compileJava` and `build` succeed with 0 errors.
- **Dedicated server:** starts and reaches `Done`.
- **Client:** launches, connects to the dedicated server and enters the world.
- **Structures:** the structure registry, premade registry and example structures load on the
  client (21 premades); the server loads 20 premades.
- **Recipes:** the custom `crafting_shaped_premade` serializer is registered and the shipped
  recipes load.
- **Tiles:** tile blocks can be placed on the client and server; they render through
  `BETilesRenderer` with cropped UVs and correct full-block lighting.
- **Preview:** the placement preview is visible and renders the real structure blocks.
- **Items:** `multi_tiles` and `premade` use a 3D item renderer for inventory icons.\n- **Ticking/interaction:** server/client level ticks, entity tracking events, structure activation\n  and pick-block are wired through Fabric events/mixins.\n- **Breaking:** breaking a tile block as a non-creative player now drops its tiles/structure item.\n- **Localization:** a modern `ru_ru.json` is generated from the legacy `ru_RU.lang`.

Known runtime issues and temporary shortcuts:

- Some common classes still reference client-only classes, so the server logs `Cannot load class`
  for `RenderBox`/`SoundInstance` in a few paths and skips those example/premade paths.
- Animated structures (`doors`, moving structures) are not fully rendered yet; the chunk
  pipeline is currently disabled for tile blocks in favour of `BETilesRenderer`.
- CreativeCore client model helpers (`CreativeBakedBoxModel`, `CreativeItemModel`,
  `QuadLighter`) are still simplified; the full chunk/model pipeline is not parity yet.
- Tile face culling, translucent tiles, ambient occlusion and break particles are incomplete.
- Invalid placement can fail silently; a user-facing message is not implemented yet.
- Sodium, The One Probe, Chisel/ChiselsAndBits and Oculus are disabled for this pass.
- The `littletiles:missing` block/item model has a temporary fallback texture.

The next stage is Stage 6 in `PLAN.md`: server/client separation and stability, followed by the
remaining rendering and structure work.

## Русский

Это **рабочая ветка порта**. Базовая ветка `1.20.1` содержит рабочую основу; здесь лежат
импортированные исходники upstream и Fabric-порт.

- Исходники: LittleTiles `1.20`, коммит `c5694e16070fd10460a093df817149128936ba86`.
- Импортировано: 618 основных Java-файлов, 158 файлов данных, ресурсы и конфигурация миксинов.
- **Сборка:** `compileJava` и `build` проходят с 0 ошибок.
- **Выделенный сервер:** запускается и доходит до `Done`.
- **Клиент:** запускается, подключается к серверу и входит в мир.
- **Структуры:** реестр структур, реестр премейдов и примеры структур загружаются на клиенте
  (21 премейд); сервер загружает 20 премейдов.
- **Рецепты:** сериализатор `crafting_shaped_premade` зарегистрирован, штатные рецепты
  загружаются.
- **Тайлы:** блоки тайлов ставятся на клиенте и сервере и рендерятся через `BETilesRenderer`
  с обрезанными UV и корректным освещением полных блоков.
- **Превью:** превью установки видно, оно показывает реальные блоки структуры.
- **Предметы:** у `multi_tiles` и `premade` есть 3D-иконки структур в инвентаре.\n- **Тики/взаимодействие:** тики уровней, события трекинга сущностей, активация структур и\n  pick-block подключены через Fabric-события и миксины.\n- **Ломание:** при ломании блока тайлов не-креативным игроком выпадают тайлы/предмет структуры.\n- **Локализация:** современный `ru_ru.json` сгенерирован из старого `ru_RU.lang`.

Известные проблемы и временные упрощения:

- Часть общих классов всё ещё ссылается на клиентские классы, поэтому сервер пишет
  `Cannot load class` для `RenderBox`/`SoundInstance` и пропускает некоторые пути примеров
  и премейдов.
- Анимированные структуры (двери, движущиеся структуры) пока не рендерятся полностью;
  chunk pipeline для блоков тайлов отключён в пользу `BETilesRenderer`.
- Клиентские модельные классы CreativeCore (`CreativeBakedBoxModel`, `CreativeItemModel`,
  `QuadLighter`) всё ещё упрощены; полного паритета с upstream нет.
- Отсечение граней, прозрачные тайлы, ambient occlusion и частицы ломания не доделаны.
- При неудачной установке нет понятного сообщения — она может молча не срабатывать.
- Sodium, The One Probe, Chisel/ChiselsAndBits и Oculus отключены для этого прохода.
- У блока/предмета `littletiles:missing` временная запасная текстура.

Следующий этап — этап 6 в `PLAN.md`: разделение клиента и сервера, стабильность, затем
оставшийся рендер и структуры.
