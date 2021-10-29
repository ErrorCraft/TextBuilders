package errorcraft.textbuilders.text.builder.builders;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import errorcraft.textbuilders.text.builder.TextBuilder;
import errorcraft.textbuilders.text.builder.TextBuilderType;
import errorcraft.textbuilders.text.builder.TextBuilderTypes;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.provider.score.LootScoreProvider;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.scoreboard.ScoreboardPlayerScore;
import net.minecraft.util.JsonHelper;

public class ScoreTextBuilder implements TextBuilder {
	private final LootScoreProvider targetProvider;
	private final String scoreboard;

	public ScoreTextBuilder(LootScoreProvider targetProvider, String scoreboard) {
		this.targetProvider = targetProvider;
		this.scoreboard = scoreboard;
	}

	@Override
	public TextBuilderType getType() {
		return TextBuilderTypes.SCORE;
	}

	@Override
	public String apply(LootContext lootContext) {
		String name = this.targetProvider.getName(lootContext);
		if (name == null) {
			return "";
		}
		Scoreboard scoreboard = lootContext.getWorld().getScoreboard();
		ScoreboardObjective scoreboardObjective = scoreboard.getNullableObjective(this.scoreboard);
		if (scoreboardObjective == null) {
			return "";
		}
		if (!scoreboard.playerHasObjective(name, scoreboardObjective)) {
			return "";
		}
		ScoreboardPlayerScore scoreboardPlayerScore = scoreboard.getPlayerScore(name, scoreboardObjective);
		return Integer.toString(scoreboardPlayerScore.getScore());
	}

	public static class Serialiser implements TextBuilder.Serialiser<ScoreTextBuilder> {
		@Override
		public void toJson(JsonObject json, ScoreTextBuilder object, JsonSerializationContext context) {
			json.add("target", context.serialize(object.targetProvider));
			json.addProperty("score", object.scoreboard);
		}

		@Override
		public ScoreTextBuilder fromJson(JsonObject json, JsonDeserializationContext context) {
			LootScoreProvider targetProvider = JsonHelper.deserialize(json, "target", context, LootScoreProvider.class);
			String scoreboard = JsonHelper.getString(json, "score");
			return new ScoreTextBuilder(targetProvider, scoreboard);
		}
	}
}
