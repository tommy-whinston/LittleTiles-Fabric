**Original developer / Оригинальный разработчик:** [LittleTiles](https://github.com/CreativeMD/LittleTiles) by **CreativeMD** and contributors (**N247S** and others).
**This project / Этот проект:** unofficial **Fabric 1.20.1** port, **made with AI assistance / сделан с помощью ИИ**. Not affiliated with or endorsed by the original author.
**License / Лицензия:** LGPL-3.0-only, same as the original. See [LICENSE](LICENSE).

# LittleTiles-Fabric

## English

Unofficial Fabric 1.20.1 port of [LittleTiles](https://github.com/CreativeMD/LittleTiles).

### Status

The project is being ported in stages. The current stage is the **port foundation**:

- Fabric 1.20.1 toolchain and entrypoints.
- CreativeCore Fabric dependency.
- **Mojang official mappings** (the upstream LittleTiles 1.20 source uses them, so the port can
  be compared with the original line by line).
- A small Forge compatibility layer under `src/main/java/net/minecraftforge` plus a Fabric
  replacement for CreativeCore's `BlockEntityCreative`. It is compile-only for now and is not
  used by the running mod yet.

The upstream 656-file / ~77k-line source is **not imported yet**. The next stage is to copy the
`1.20` source in and fix it module by module. See [PORTING.md](PORTING.md).

> This is an early **base skeleton**, not a playable port yet.

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

Проект переносится поэтапно. Сейчас готов **фундамент порта**:

- Сборочная система Fabric 1.20.1 и точки входа.
- Зависимость от CreativeCore Fabric.
- **Официальные маппинги Mojang** — исходники LittleTiles 1.20 написаны под них, поэтому порт
  можно сравнивать с оригиналом строка в строку.
- Небольшой слой совместимости с Forge в `src/main/java/net/minecraftforge` и Fabric-замена
  `BlockEntityCreative` из CreativeCore. Пока он нужен только для компиляции и в запущенном
  моде не используется.

Исходники оригинала (656 файлов, ~77 тыс. строк) **ещё не импортированы**. Следующий этап —
перенести код из ветки `1.20` и разбирать его по модулям. Подробнее — в [PORTING.md](PORTING.md).

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
