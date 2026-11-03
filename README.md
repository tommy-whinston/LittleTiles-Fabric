**Оригинальный разработчик / Original developer:** [LittleTiles](https://github.com/CreativeMD/LittleTiles) by **CreativeMD** and contributors (**N247S** and others).
**Этот проект / This project:** unofficial **Fabric 1.20.1** port, **made with AI assistance / сделан с помощью ИИ**. Not affiliated with or endorsed by the original author.
**Лицензия / License:** LGPL-3.0-only, same as the original. See [LICENSE](LICENSE).

# LittleTiles-Fabric

Unofficial Fabric 1.20.1 port of [LittleTiles](https://github.com/CreativeMD/LittleTiles).

> This is an early **base skeleton**, not a playable port yet. It sets up the Fabric 1.20.1
> toolchain, the required CreativeCore dependency, the entrypoints and the project structure
> for the actual port.

## Requirements

| | |
|---|---|
| Minecraft | 1.20.1 |
| Fabric Loader | 0.16.14+ |
| Fabric API | 0.92.6+1.20.1 |
| CreativeCore (Fabric) | 2.12.41 (2.12.30+) |
| Java | 17+ (dev toolchain: 21) |

CreativeCore is **not bundled** with this mod. Install the Fabric build of CreativeCore separately.

## Build

```bash
./gradlew build
```

The remapped jar is written to `build/libs/littletiles-fabric-0.1.0-alpha.1.jar`.

## Development

```bash
./gradlew runClient
```

## Project layout

```
src/main/java/team/creative/littletiles/        main entrypoint and common code
src/main/java/team/creative/littletiles/client/ client entrypoint and client code
src/main/resources/                             fabric.mod.json, mixins, assets
```

The upstream package names are kept (`team.creative.littletiles`) so the port can be compared
with the original source and, later, so addons can be recompiled against it.

See [PORTING.md](PORTING.md) for the upstream commit, the known CreativeCore dependency gap
and a suggested porting order.

## Roadmap

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

## Credits

- **CreativeMD** — author of the original LittleTiles.
- **N247S** and the LittleTiles contributors — original code and design.
- **CreativeCore** — required library; its Fabric build is used by this port.
- The mod icon is the original LittleTiles artwork by CreativeMD.
- The Fabric port and its documentation were produced with **AI assistance**.

This project is a derivative work of LittleTiles. Original code is Copyright (C) CreativeMD and
contributors and is licensed under LGPL-3.0-only. Changes in this repository are released under
the same license.
