# Port status / Статус порта

## English

This is the **work-in-progress port branch**. The default `1.20.1` branch contains the working
base; this branch contains the imported upstream source.

- Upstream source: LittleTiles `1.20`, commit `c5694e16070fd10460a093df817149128936ba86`
- Imported: 618 main Java files, 158 data files, assets and the upstream mixin configuration.
- **Compile status: `compileJava` and `build` succeed with 0 errors.**
- **Dedicated server: starts successfully and reaches `Done (17.274s)!`**
- Client launch and rendering: **not tested yet**.

Known runtime issues and temporary shortcuts:

- Structure registration currently throws while loading client-only classes; it is wrapped in a
  try/catch, so the server starts but structures are not initialized.
- Example structures fail to load; custom `crafting_shaped_premade` recipes are not registered.
- Several client/render pieces are temporary stubs or no-ops (`ModelData`, `QuadLighter`,
  CreativeCore model classes, buffer uploads, capabilities and some interaction paths).
- Sodium, The One Probe, Chisel/ChiselsAndBits and Oculus are disabled for this pass.
- Some client-only behavior was removed from common classes so the dedicated server can load
  them (tool handler, client interaction paths, client-only particles).

The next stage is the client entrypoint/launch and the first visual test.

## Русский

Это **рабочая ветка порта**. В основной ветке `1.20.1` лежит рабочая база; здесь находятся
импортированные исходники оригинала.

- Исходники: LittleTiles `1.20`, коммит `c5694e16070fd10460a093df817149128936ba86`
- Импортировано: 618 основных Java-файлов, 158 файлов данных, ресурсы и оригинальный конфиг
  миксинов.
- **Статус компиляции: `compileJava` и `build` проходят без ошибок.**
- **Выделенный сервер: успешно запускается и доходит до `Done (17.274s)!`**
- Запуск клиента и рендер: **ещё не тестировались**.

Известные проблемы и временные упрощения:

- Регистрация структур пока падает при загрузке клиентских классов; она обёрнута в try/catch,
  поэтому сервер стартует, но структуры не инициализируются.
- Примеры структур не загружаются; кастомные рецепты `crafting_shaped_premade` не
  зарегистрированы.
- Часть клиента и рендера — временные заглушки или no-op (`ModelData`, `QuadLighter`,
  модельные классы CreativeCore, загрузка буферов, capabilities и некоторые пути
  взаимодействия).
- Sodium, The One Probe, Chisel/ChiselsAndBits и Oculus отключены для этого прохода.
- Из общих классов убрано часть клиентского поведения, чтобы выделенный сервер мог их
  загрузить (tool handler, клиентские пути взаимодействия, клиентские частицы).

Следующий этап — точка входа клиента, запуск клиента и первый визуальный тест.
