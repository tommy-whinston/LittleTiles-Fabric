# Port status / Статус порта

## English

This is the **work-in-progress port branch**. The default `1.20.1` branch contains the working
base; this branch contains the imported upstream source.

- Upstream source: LittleTiles `1.20`, commit `c5694e16070fd10460a093df817149128936ba86`
- Imported: 618 main Java files, 158 data files, assets and the upstream mixin configuration.
- **Compile status: `compileJava` and `build` succeed with 0 errors.**
- The compiled jar is produced, but the mod has **not** been started yet.
- Sodium, The One Probe, Chisel/ChiselsAndBits and Oculus integrations are disabled for this
  first compile pass.
- Several client/render pieces are temporary stubs or no-ops (`ModelData`, `QuadLighter`,
  `CreativeCore` model classes, buffer uploads, capability lookups, some interaction paths).
  They compile, but they do not implement the final behavior yet.

The next stage is the Fabric entrypoint and the first runtime launch. Expect runtime crashes;
they will be fixed one by one.

## Русский

Это **рабочая ветка порта**. В основной ветке `1.20.1` лежит рабочая база; здесь находятся
импортированные исходники оригинала.

- Исходники: LittleTiles `1.20`, коммит `c5694e16070fd10460a093df817149128936ba86`
- Импортировано: 618 основных Java-файлов, 158 файлов данных, ресурсы и оригинальный конфиг
  миксинов.
- **Статус компиляции: `compileJava` и `build` проходят без ошибок.**
- Jar собирается, но мод **ещё не запускался**.
- Интеграции Sodium, The One Probe, Chisel/ChiselsAndBits и Oculus отключены для первого
  прохода компиляции.
- Часть клиента и рендера — временные заглушки или no-op (`ModelData`, `QuadLighter`,
  модельные классы CreativeCore, загрузка буферов, capabilities, некоторые пути
  взаимодействия). Они компилируются, но финального поведения пока не реализуют.

Следующий этап — точка входа Fabric и первый запуск. Стоит ожидать крашей; будем исправлять
их по одному.
