# Port status / Статус порта

## English

This is the **work-in-progress port branch**. The default `1.20.1` branch contains the working
base; this branch contains the imported upstream source.

- Upstream source: LittleTiles `1.20`, commit `c5694e16070fd10460a093df817149128936ba86`
- Imported: 618 main Java files, 158 data files, assets and the upstream mixin configuration.
- **Compile status: `compileJava` and `build` succeed with 0 errors.**
- **Dedicated server: starts successfully and reaches `Done (17.274s)!`**
- **Client: launches, initializes LWJGL/OpenAL, reloads resources and builds the texture
  atlases.** The process stays alive at this point; a world/gameplay test has not been done yet.

Known runtime issues and temporary shortcuts:

- Structure registration currently throws while loading client-only classes; it is wrapped in a
  try/catch, so the game starts but structures are not initialized.
- Example structures fail to load; custom `crafting_shaped_premade` recipes are not registered.
- The `littletiles:missing` model is missing its inventory/block model.
- CreativeCore config loading prints stack traces during client startup.
- Several client/render pieces are temporary stubs or no-ops (`ModelData`, `QuadLighter`,
  CreativeCore model classes, buffer uploads, capabilities and some interaction paths).
- Sodium, The One Probe, Chisel/ChiselsAndBits and Oculus are disabled for this pass.
- Some client-only behavior was removed from common classes so the dedicated server can load
  them (tool handler, client interaction paths, client-only particles).

The next stage is entering a world and testing tile placement/rendering.

## Русский

Это **рабочая ветка порта**. В основной ветке `1.20.1` лежит рабочая база; здесь находятся
импортированные исходники оригинала.

- Исходники: LittleTiles `1.20`, коммит `c5694e16070fd10460a093df817149128936ba86`
- Импортировано: 618 основных Java-файлов, 158 файлов данных, ресурсы и оригинальный конфиг
  миксинов.
- **Статус компиляции: `compileJava` и `build` проходят без ошибок.**
- **Выделенный сервер: успешно запускается и доходит до `Done (17.274s)!`**
- **Клиент: запускается, инициализирует LWJGL/OpenAL, перезагружает ресурсы и собирает
  текстурные атласы.** Процесс остаётся живым; тест входа в мир и геймплея ещё не проводился.

Известные проблемы и временные упрощения:

- Регистрация структур пока падает при загрузке клиентских классов; она обёрнута в try/catch,
  поэтому игра стартует, но структуры не инициализируются.
- Примеры структур не загружаются; кастомные рецепты `crafting_shaped_premade` не
  зарегистрированы.
- У модели `littletiles:missing` отсутствует inventory/block-модель.
- Загрузка конфига CreativeCore печатает stack trace при старте клиента.
- Часть клиента и рендера — временные заглушки или no-op (`ModelData`, `QuadLighter`,
  модельные классы CreativeCore, загрузка буферов, capabilities и некоторые пути
  взаимодействия).
- Sodium, The One Probe, Chisel/ChiselsAndBits и Oculus отключены для этого прохода.
- Из общих классов убрано часть клиентского поведения, чтобы выделенный сервер мог их
  загрузить (tool handler, клиентские пути взаимодействия, клиентские частицы).

Следующий этап — вход в мир и проверка установки/рендера тайлов.
