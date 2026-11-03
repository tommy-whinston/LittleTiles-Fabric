**Original developer / Оригинальный разработчик:** [LittleTiles](https://github.com/CreativeMD/LittleTiles) by **CreativeMD** and contributors (**N247S** and others).
**This project / Этот проект:** unofficial **Fabric 1.20.1** port, **made with AI assistance / сделан с помощью ИИ**. Not affiliated with or endorsed by the original author.
**License / Лицензия:** LGPL-3.0-only, same as the original. See [LICENSE](LICENSE).

# LittleTiles-Fabric

## English

Unofficial Fabric 1.20.1 port of [LittleTiles](https://github.com/CreativeMD/LittleTiles).

### Status

The project is being ported in stages. The `port` branch now contains the imported upstream
source (618 main Java files, data, assets, mixins) and **compiles and builds with 0 errors**.
The default `1.20.1` branch remains the buildable base.

The Fabric entrypoints are adapted, the **dedicated server starts successfully** (`Done`) and the
**client launches, connects to a server and enters the world**. Placing a tile block and checking
its rendering are the next stage. Several client/render pieces are still temporary stubs or
no-ops (`ModelData`, `QuadLighter`, CreativeCore model classes, buffer uploads, capabilities and
some interaction paths), so the mod is **not fully playable yet**. Sodium, The One Probe,
Chisel/ChiselsAndBits and Oculus are disabled for the first compile pass.

See [PORTING.md](PORTING.md) and the staged [PLAN.md](PLAN.md).

### Requirements

| | |
|---|---|
| Minecraft | 1.20.1 |
| Fabric Loader | 0.16.14+ |
| Fabric API | 0.92.6+1.20.1 |
| CreativeCore (Fabric) | 2.12.41 (2.12.30+) |
| Java | 17+ (dev toolchain: 21) |

CreativeCore is **not bundled** with this mod. Install the Fabric build of CreativeCore separately.

### Build

```bash
./gradlew build
```

The remapped jar is written to `build/libs/littletiles-fabric-0.1.0-alpha.1.jar`.

### Development

```bash
./gradlew runClient
```

### Project layout

```
src/main/java/team/creative/littletiles/        main entrypoint and common code
src/main/java/team/creative/littletiles/client/ client entrypoint and client code
src/main/resources/                             fabric.mod.json, mixins, assets
```

The upstream package names are kept (`team.creative.littletiles`) so the port can be compared
with the original source and, later, so addons can be recompiled against it.

### Roadmap

- [ ] Registries: blocks, items, block entity types, sounds, recipe types
- [ ] Core tile data model and block entities
- [ ] Networking through CreativeCore's packet system
- [ ] Chunk data storage and synchronization
- [ ] Custom tile/chunk rendering pipeline
- [ ] Tools, recipes, GUIs and structures
- [ ] Fabric API events / mixins replacing Forge events
- [ ] Item/block models on Fabric's model loading API
- [ ] Sodium compatibility (optional, after the vanilla renderer works)
- [ ] Multiplayer and world compatibility testing

### Credits

- **CreativeMD** — author of the original LittleTiles.
- **N247S** and the LittleTiles contributors — original code and design.
- **CreativeCore** — required library; its Fabric build is used by this port.
- The mod icon is the original LittleTiles artwork by CreativeMD.
- The Fabric port and its documentation were produced with **AI assistance**.

---

## Русский

Неофициальный Fabric-порт [LittleTiles](https://github.com/CreativeMD/LittleTiles) для Minecraft 1.20.1.

### Статус

Проект переносится поэтапно. В ветке `port` теперь лежат импортированные исходники оригинала
(618 основных Java-файлов, данные, ресурсы, миксины), и она **компилируется и собирается без
ошибок**. Основная ветка `1.20.1` остаётся рабочей базой.

Точки входа Fabric адаптированы, **выделенный сервер успешно запускается** (`Done`), а
**клиент запускается, подключается к серверу и входит в мир**. Следующий этап — поставить блок
тайлов и проверить его рендер. Часть клиента и рендера пока являются временными заглушками или
no-op (`ModelData`, `QuadLighter`, модельные классы CreativeCore, загрузка буферов, capabilities
и некоторые пути взаимодействия), поэтому мод **ещё не полностью играбельный**. Sodium, The One
Probe, Chisel/ChiselsAndBits и Oculus отключены для первого прохода компиляции.

Подробнее — в [PORTING.md](PORTING.md) и поэтапном [PLAN.md](PLAN.md).

> Это ранний **каркас**, а не играбельный порт.

### Требования

| | |
|---|---|
| Minecraft | 1.20.1 |
| Fabric Loader | 0.16.14+ |
| Fabric API | 0.92.6+1.20.1 |
| CreativeCore (Fabric) | 2.12.41 (2.12.30+) |
| Java | 17+ (для разработки: 21) |

CreativeCore **не встроен** в мод. Установите Fabric-сборку CreativeCore отдельно.

### Сборка

```bash
./gradlew build
```

Готовый jar появится в `build/libs/littletiles-fabric-0.1.0-alpha.1.jar`.

### Разработка

```bash
./gradlew runClient
```

### Структура проекта

```
src/main/java/team/creative/littletiles/        главная точка входа и общий код
src/main/java/team/creative/littletiles/client/ клиентская точка входа и клиентский код
src/main/resources/                             fabric.mod.json, миксины, ресурсы
```

Пакеты оригинала сохранены (`team.creative.littletiles`), чтобы порт можно было сравнивать
с исходниками и позже перекомпилировать под него аддоны.

### План

- [ ] Реестры: блоки, предметы, типы block entity, звуки, типы рецептов
- [ ] Базовая модель тайлов и block entities
- [ ] Сеть через пакетную систему CreativeCore
- [ ] Хранение и синхронизация данных чанков
- [ ] Собственный рендер тайлов и чанков
- [ ] Инструменты, рецепты, GUI и структуры
- [ ] События Fabric API / миксины вместо событий Forge
- [ ] Модели предметов и блоков на API загрузки моделей Fabric
- [ ] Совместимость с Sodium (опционально, после обычного рендера)
- [ ] Тестирование мультиплеера и совместимости миров

### Благодарности

- **CreativeMD** — автор оригинального LittleTiles.
- **N247S** и участники LittleTiles — оригинальный код и дизайн.
- **CreativeCore** — обязательная библиотека; в порте используется её Fabric-сборка.
- Иконка мода — оригинальная работа CreativeMD.
- Порт и документация созданы с **помощью ИИ**.

### Лицензия

Это производная работа от LittleTiles. Оригинальный код принадлежит CreativeMD и участникам
и распространяется под LGPL-3.0-only. Изменения в этом репозитории распространяются под той же
лицензией.
