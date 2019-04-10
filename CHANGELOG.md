# CHANGELOG
## 1.3.2
### Fixed
- Fixed game crash when loading world with existing redstone comparators. Closes #5
- Fixed colored redstone comparator resetting its color after world reload.
## 1.3.1
### Changed
- Updated Chinese translation.
## 1.3.0
### Added
- New colored property - Alienated - cyan blocks have a chance to drop Enderman loot instead of actual block item. Default: 5% chance to drop.
- New colored property - Fishy - light blue blocks have a chance to drop fishing loot instead of actual block item. Default: 5% chance to drop.
- New colored property - Soft - purple blocks reduce fall damage. Default: 50% fall damage reduction.
- New colored property - Edible - magenta items can be eaten. To eat an item press Shift + Right Click (**Placing item/block has priority over eating!**). By default eating magenta item restores 2 points of Hunger and 0.3 points of Saturation. There is also 5% chance to get Saturation effect for 1 second and/or Nausea effect for 15 seconds.
- All of new colored properties can be enabled/disabled in mod config. Specific parameters of properties can also be customized in config.
- Added Chinese translation (Thanks **pixelgear_**).
### Changed
- Small changes to English config translation.
## 1.2.1
### Fixed
- Added missing entry in polish translation.
- Fixed the issue with Colored Redstone Lamp not emitting light (client-side visual bug).
## 1.2.0
### Added
- Added option to enable/disable in-world recoloring in mod config.
- Added Albedo integration for Colored Redstone Torches (colored light). Colored light for torches can be enabled/disabled in mod config. **Requires [Albedo](https://minecraft.curseforge.com/projects/albedo) mod.**
- Added Colored Redstone Lamps (16 color variants). Shade of the lamp and its light level depends on the strength of provided redstone signal.
- Added Albedo integration for Colored Redstone Lamps (colored light). Colored light for lamps can be enabled/disabled in mod config. **Requires [Albedo](https://minecraft.curseforge.com/projects/albedo) mod.**
- New colored property - Slimy - lime blocks have a chance to spawn slime when broken. Default: 5% chance to spawn slime.
- New colored property - Withering - black blocks have a chance to apply wither effect to entity standing on them. Default: 15% chance to apply effect for 100 ticks.
- New colored property - Sluggish - gray blocks have a chance to apply slowness effect to entity standing on them. Default: 15% chance to apply effect for 100 ticks.
- New colored property - Speedy - light gray blocks have a chance to apply speed effect to entity standing on them. Default: 15% chance to apply effect for 100 ticks.
- New colored property - Healthy - pink blocks have a chance to apply regeneration effect to entity standing on them. Default: 15% chance to apply effect for 100 ticks.
- All of new colored properties can be enabled/disabled in mod config. Chance and duration of effects can also be customized in config.
### Changed
- Colored property Burnable - now brown blocks can also catch on fire. This can be enabled/disabled in mod config.
### Fixed
- Fixed Redstone Lamp recipe being incorrectly displayed in Colored Redstone Block group in recipe book.
## 1.1.0
### Added
- Added Chisel integration for Colored Redstone Blocks (Redstone group).
- Added option to enable/disable colored names and tooltips for blocks and items.
- New colored property - Burnable - brown items and blocks in item form can be used as a fuel. Property can be enabled/disabled in config. In addition, burning time can be set in config (default: 200 ticks - 1 item).
- Added polish translation.
### Changed
- Changed options arrangement in the configuration file. **You may need to delete the old configuration file when updating to properly generate a new one!**
## 1.0.1
### Added
- Added translations for color names.
### Changed
- Changed black and blue color for item names and tooltips to increase readability.
## 1.0.0
- Mod released!