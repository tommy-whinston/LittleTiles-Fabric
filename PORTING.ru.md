# Заметки о переносе

[English](PORTING.md) | **Русский**

Этот репозиторий — Fabric-порт LittleTiles для Minecraft 1.20.1 на основе ветки `1.20`.

- Оригинал: <https://github.com/CreativeMD/LittleTiles/tree/1.20>
- Проверенный коммит оригинала: `c5694e16070fd10460a093df817149128936ba86` (2026-05-08)
- Обязательная библиотека: [CreativeCore](https://github.com/CreativeMD/CreativeCore), ветка `1.20`
- Используемая Fabric-сборка CreativeCore: `2.12.41` для Minecraft 1.20.1
  (ID версии на Modrinth: `3q9j0cNZ`)

## Текущее состояние

Проект собирается под Fabric 1.20.1, запускается на выделенном сервере, подключает CreativeCore
и содержит точки входа для будущего порта. Исходники LittleTiles пока не импортированы.

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

## Чего не хватает в CreativeCore

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

## Предлагаемый порядок переноса

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

## Полезные цифры

- Размер оригинала: 656 Java-файлов, ~77,6 тыс. строк.
- Импорты Forge: 432 строки в 177 файлах; 247 из них — `@OnlyIn` / `Dist`.
- 26 обработчиков `@SubscribeEvent`.
- 67 миксинов: 21 Sodium/Embeddium, 46 остальных.
- Нет `SimpleChannel`, нет `ForgeConfigSpec`.
- Capabilities: 11 упоминаний `IItemHandler`, 6 `IFluidHandler`, 5 `LazyOptional`.
- `ModelData`: 28 упоминаний.

## Лицензия

LittleTiles и CreativeCore распространяются под **LGPL-3.0-only**. Этот порт — производная
работа и выпускается под той же лицензией. Сохраняйте уведомления об авторских правах
и публикуйте исходники порта.
