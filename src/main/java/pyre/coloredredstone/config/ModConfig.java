package pyre.coloredredstone.config;

import net.minecraftforge.common.config.Config;
import pyre.coloredredstone.util.Reference;

@Config(modid = Reference.MOD_ID)
@Config.LangKey("coloredredstone.config.title")
public class ModConfig {

    @Config.LangKey("coloredredstone.config.colored_names")
    @Config.Comment("Enables/disables colored names and tooltips for blocks and items.")
    public static boolean coloredNamesAndTooltips = true;

    @Config.LangKey("coloredredstone.config.in_world_recoloring")
    @Config.Comment("Enables/disables in-world recoloring.")
    public static boolean inWorldRecoloring = true;

    @Config.LangKey("coloredredstone.config.colored_properties")
    @Config.Comment("Colored properties configuration.")
    public static ColoredProperties coloredPropertiesConfig = new ColoredProperties();

    @Config.LangKey("coloredredstone.config.integration")
    @Config.Comment("Integrations with another mods.")
    public static IntegrationConfig integrationConfig = new IntegrationConfig();

    public static class ColoredProperties {

        @Config.LangKey("coloredredstone.config.colored_properties.waterproof")
        @Config.Comment("If true, placed, blue colored redstone components will not be washed away by water.")
        public boolean waterproof = true;

        @Config.LangKey("coloredredstone.config.colored_properties.explosionproof")
        @Config.Comment("If true, orange colored items and blocks in item form will not be destroyed by explosions, placed redstone components will not pop up.")
        public boolean explosionproof = true;

        @Config.LangKey("coloredredstone.config.colored_properties.fireproof")
        @Config.Comment("If true, yellow items and blocks in item form will not be destroyed by fire or lava.")
        public boolean fireproof = true;

        @Config.LangKey("coloredredstone.config.colored_properties.despawnproof")
        @Config.Comment("If true, white items and blocks in item form will not despawn when on the ground.")
        public boolean despawnproof = true;

        @Config.LangKey("coloredredstone.config.colored_properties.cactusproof")
        @Config.Comment("If true, green items and blocks in item form will not be destroyed by Cactus.")
        public boolean cactusproof = true;

        @Config.LangKey("coloredredstone.config.colored_properties.burnable")
        @Config.Comment("If true, brown items and blocks in item form can be used as a fuel.")
        public boolean burnable = true;

        @Config.LangKey("coloredredstone.config.colored_properties.burnable_burning_time")
        @Config.Comment("Burning time of burnable items in ticks.")
        @Config.RangeInt(min = 1)
        public int burnableBurningTime = 200;

        @Config.LangKey("coloredredstone.config.colored_properties.burnable_catch_fire")
        @Config.Comment("If true, brown blocks can catch on fire.")
        public boolean burnableCatchFire = true;

        @Config.LangKey("coloredredstone.config.colored_properties.slimy")
        @Config.Comment("If true, lime blocks have a chance to spawn slime when broken.")
        public boolean slimy = true;

        @Config.LangKey("coloredredstone.config.colored_properties.slimy_chance")
        @Config.Comment("Chance of spawning slime.")
        @Config.RangeInt(min = 1, max = 100)
        public int slimyChance = 5;

        @Config.LangKey("coloredredstone.config.colored_properties.withering")
        @Config.Comment("If true, black blocks have a chance to apply wither effect to entity standing on them.")
        public boolean withering = true;

        @Config.LangKey("coloredredstone.config.colored_properties.withering_chance")
        @Config.Comment("Chance of applying wither effect.")
        @Config.RangeInt(min = 1, max = 100)
        public int witheringChance = 15;

        @Config.LangKey("coloredredstone.config.colored_properties.withering_duration")
        @Config.Comment("Wither effect duration in ticks.")
        @Config.RangeInt(min = 1)
        public int witheringDuration = 100;

        @Config.LangKey("coloredredstone.config.colored_properties.sluggish")
        @Config.Comment("If true, gray blocks have a chance to apply slowness effect to entity standing on them.")
        public boolean sluggish = true;

        @Config.LangKey("coloredredstone.config.colored_properties.sluggish_chance")
        @Config.Comment("Chance of applying slowness effect.")
        @Config.RangeInt(min = 1, max = 100)
        public int sluggishChance = 15;

        @Config.LangKey("coloredredstone.config.colored_properties.sluggish_duration")
        @Config.Comment("Slowness effect duration in ticks.")
        @Config.RangeInt(min = 1)
        public int sluggishDuration = 100;

        @Config.LangKey("coloredredstone.config.colored_properties.speedy")
        @Config.Comment("If true, light gray blocks have a chance to apply speed effect to entity standing on them.")
        public boolean speedy = true;

        @Config.LangKey("coloredredstone.config.colored_properties.speedy_chance")
        @Config.Comment("Chance of applying speed effect.")
        @Config.RangeInt(min = 1, max = 100)
        public int speedyChance = 15;

        @Config.LangKey("coloredredstone.config.colored_properties.speedy_duration")
        @Config.Comment("Speed effect duration in ticks.")
        @Config.RangeInt(min = 1)
        public int speedyDuration = 100;

        @Config.LangKey("coloredredstone.config.colored_properties.healthy")
        @Config.Comment("If true, pink blocks have a chance to apply regeneration effect to entity standing on them.")
        public boolean healthy = true;

        @Config.LangKey("coloredredstone.config.colored_properties.healthy_chance")
        @Config.Comment("Chance of applying regeneration effect.")
        @Config.RangeInt(min = 1, max = 100)
        public int healthyChance = 15;

        @Config.LangKey("coloredredstone.config.colored_properties.healthy_duration")
        @Config.Comment("Regeneration effect duration in ticks.")
        @Config.RangeInt(min = 1)
        public int healthyDuration = 100;

        @Config.LangKey("coloredredstone.config.colored_properties.alienated")
        @Config.Comment("If true, cyan blocks have a chance to drop Enderman loot instead of actual block item.")
        public boolean alienated = true;

        @Config.LangKey("coloredredstone.config.colored_properties.alienated_chance")
        @Config.Comment("Chance of dropping Enderman loot.")
        @Config.RangeInt(min = 1, max = 100)
        public int alienatedChance = 5;

        @Config.LangKey("coloredredstone.config.colored_properties.fishy")
        @Config.Comment("If true, light blue blocks have a chance to drop fishing loot instead of actual block item.")
        public boolean fishy = true;

        @Config.LangKey("coloredredstone.config.colored_properties.fishy_chance")
        @Config.Comment("Chance of dropping fishing loot.")
        @Config.RangeInt(min = 1, max = 100)
        public int fishyChance = 5;

        @Config.LangKey("coloredredstone.config.colored_properties.soft")
        @Config.Comment("If true, purple blocks reduce fall damage.")
        public boolean soft = true;

        @Config.LangKey("coloredredstone.config.colored_properties.soft_reduction")
        @Config.Comment("Percentage of damage reduction.")
        @Config.RangeInt(min = 1, max = 100)
        public int softDamageReduction = 50;

        @Config.LangKey("coloredredstone.config.colored_properties.edible")
        @Config.Comment("If true, magenta items can be eaten.")
        public boolean edible = true;

        @Config.LangKey("coloredredstone.config.colored_properties.edible_hunger")
        @Config.Comment("Number of Hunger points restored when eaten.")
        @Config.RangeInt(min = 1, max = 20)
        public int edibleHunger = 2;

        @Config.LangKey("coloredredstone.config.colored_properties.edible_saturation")
        @Config.Comment("Number of Saturation points restored when eaten.")
        @Config.RangeDouble(min = 0.1F, max = 20F)
        public float edibleSaturation = 0.3F;

        @Config.LangKey("coloredredstone.config.colored_properties.edible_saturation_chance")
        @Config.Comment("Chance of applying Saturation effect.")
        @Config.RangeInt(min = 0, max = 100)
        public int edibleSaturationChance = 5;

        @Config.LangKey("coloredredstone.config.colored_properties.edible_saturation_duration")
        @Config.Comment("Saturation effect duration in ticks.")
        @Config.RangeInt(min = 1)
        public int edibleSaturationDuration = 20;

        @Config.LangKey("coloredredstone.config.colored_properties.edible_nausea_chance")
        @Config.Comment("Chance of applying Nausea effect.")
        @Config.RangeInt(min = 0, max = 100)
        public int edibleNauseaChance = 5;

        @Config.LangKey("coloredredstone.config.colored_properties.edible_nausea_duration")
        @Config.Comment("Nausea effect duration in ticks.")
        @Config.RangeInt(min = 1)
        public int edibleNauseaDuration = 300;
    }
}
