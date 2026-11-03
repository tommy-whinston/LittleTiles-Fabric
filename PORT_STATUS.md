# Port status / Статус порта

## English

This is the **work-in-progress port branch**. The default `1.20.1` branch contains the working
base; this branch contains the imported upstream source and does not compile yet.

- Upstream source: LittleTiles `1.20`, commit `c5694e16070fd10460a093df817149128936ba86`
- Imported: 618 main Java files, 158 data files, assets and the upstream mixin configuration.
- Current compile baseline: **1,095 errors** (`./gradlew compileJava`).
- Optional integrations removed for the first pass: The One Probe, Chisel/ChiselsAndBits API,
  Oculus. Sodium 0.5.11 is available as a compile-only dependency.

The largest remaining error groups are the Forge client events/model data, Forge-added vanilla
methods (generic `getBlockEntity`, `getModelDataManager`, `getFriction`, `getLightEmission`),
`EntityRenderers.register` access, and the CreativeCore client model classes.

Do not merge this branch into `1.20.1` until it compiles and starts.

---

## Русский

Это **рабочая ветка порта**. В основной ветке `1.20.1` лежит рабочая база; здесь находятся
импортированные исходники оригинала, и проект пока не собирается.

- Исходники: LittleTiles `1.20`, коммит `c5694e16070fd10460a093df817149128936ba86`
- Импортировано: 618 основных Java-файлов, 158 файлов данных, ресурсы и оригинальный конфиг
  миксинов.
- Текущая база ошибок компиляции: **1 095** (`./gradlew compileJava`).
- Для первого прохода убраны опциональные интеграции: The One Probe, Chisel/ChiselsAndBits API,
  Oculus. Sodium 0.5.11 подключён как compile-only зависимость.

Основные оставшиеся группы ошибок: клиентские события и model data Forge, методы, которые
Forge добавляет в ванильные классы (generic `getBlockEntity`, `getModelDataManager`, `getFriction`,
`getLightEmission`), доступ к `EntityRenderers.register` и клиентские модельные классы
CreativeCore.

Не сливать эту ветку в `1.20.1`, пока она не соберётся и не запустится.
