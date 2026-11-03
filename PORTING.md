# Porting notes

[English](PORTING.md) | [Русский](PORTING.ru.md)

This repository is a Fabric 1.20.1 port of the upstream LittleTiles `1.20` branch.

- Upstream: <https://github.com/CreativeMD/LittleTiles/tree/1.20>
- Audited upstream commit: `c5694e16070fd10460a093df817149128936ba86` (2026-05-08)
- Required library: [CreativeCore](https://github.com/CreativeMD/CreativeCore) `1.20` branch
- CreativeCore Fabric build used by this project: `2.12.41` for Minecraft 1.20.1
  (Modrinth version ID `3q9j0cNZ`)

## Current state

The Fabric project compiles against 1.20.1, starts on a dedicated server, declares CreativeCore
and contains the entrypoints for the actual port. The upstream LittleTiles source is not
imported yet.

The project uses **Mojang official mappings** because the upstream `1.20` branch is written
against them. This avoids remapping the whole 77k-line source by hand and lets the port be
compared with upstream directly.

A temporary Forge compatibility layer is in place under:

- `src/main/java/net/minecraftforge/` — event bus bridge, `DeferredRegister`,
  `RegistryObject`, `ForgeRegistries`, FML shims, the event classes LittleTiles uses, and
  basic capability interfaces.
- `src/main/java/team/creative/creativecore/common/be/BlockEntityCreative.java` — Fabric
  replacement for the Forge-only CreativeCore base block entity.

It is compile-only for now. As the port progresses, the compatibility classes will be replaced
with native Fabric API calls and mixins.

## Known dependency gap

The published CreativeCore Fabric jar does not contain a few Forge-only classes that
LittleTiles uses. `BlockEntityCreative` has already been added to this project. The remaining
client-side classes are:

- `team.creative.creativecore.client.render.model.CreativeBlockModel`
- `team.creative.creativecore.client.render.model.CreativeBakedBoxModel`
- `team.creative.creativecore.client.render.model.CreativeItemModel`
- `team.creative.creativecore.client.render.model.CreativeItemBoxModel`
- `team.creative.creativecore.client.render.model.CreativeQuadLighter`
- `team.creative.creativecore.mixin.ForgeModelBlockRendererAccessor`

Internally those classes also need `CreativeBakedModel`, `CreativeUnbakedModel` and
`CreativeModelLoader`. Together this is roughly 600 lines of code. Port them into this project
before porting LittleTiles' client code.

## Suggested port order

1. **Registries.** Replace `DeferredRegister` / `RegistryObject` with Fabric registries.
   There are about 61 `RegistryObject` references and 17 `ForgeRegistries` references.
2. **Common data model.** Port the tile data, `BETiles` and the structure code. Most of this is
   vanilla API and should compile with few changes.
3. **Networking.** Keep CreativeCore's `CreativeNetwork`; LittleTiles does not use Forge's
   `SimpleChannel`.
4. **Events and capabilities.** Replace the 26 `@SubscribeEvent` handlers with Fabric API
   callbacks or mixins. Port `IItemHandler` / `IFluidHandler` / `LazyOptional` to the Fabric
   Transfer API or small adapters.
5. **Block entities and chunk data.** Port `BlockEntityCreative` and the chunk serializer hooks.
6. **Client rendering.** Port the mixins and the render pipeline. There are 67 mixins in
   upstream; 21 of them are Sodium/Embeddium compatibility.
7. **Models.** Rewrite the Forge model loader hooks (`ModelEvent.RegisterGeometryLoaders`,
   `ModelData`, `IClientBlockExtensions`) on Fabric's model loading API.
8. **Sodium.** Optional for the first playable build. Fabric Sodium 0.5 uses the same
   `me.jellysquid.mods.sodium` package as Embeddium, so much of the compatibility code can
   probably be reused; the Embeddium-only translucent sorter needs a separate solution.
9. **Testing.** Single-player, dedicated server, multiplayer, old worlds and the main tools.

## Useful numbers

- Upstream size: 656 Java files, ~77.6k lines.
- Forge imports: 432 lines across 177 files; 247 of those are `@OnlyIn` / `Dist` imports.
- 26 `@SubscribeEvent` handlers.
- 67 mixins: 21 Sodium/Embeddium, 46 other.
- No `SimpleChannel`, no `ForgeConfigSpec`.
- Capabilities: 11 `IItemHandler`, 6 `IFluidHandler`, 5 `LazyOptional` references.
- `ModelData`: 28 references.

## License

LittleTiles and CreativeCore are licensed under **LGPL-3.0-only**. This port is a derivative
work and is released under the same license. Keep the original copyright notices and make the
source of this port available.
