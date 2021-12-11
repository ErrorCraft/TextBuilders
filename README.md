# Text Builders
This is a fabric mod that adds text builders to Minecraft.

Text builders are meant to dynamically build strings for use in text components.
They are supposed to replace the hardcoded text.
This makes using text components much more powerful, as we no longer have to repeat them all the time.
- We can use numbers in strings. This means we no longer need a condition for every value!
- We can use repetitions of strings rather than repeating them in different text components.

## Using text builders
Text builders are JSON files in data packs.
You can create text builders under the `text_builders` folder in your namespace.
Inside the file you can either have one or an array of text builders. An array combines all specified text builders together.
After that you can reference the text builder with a text provider.
There's a [wiki](https://github.com/ErrorCraft/TextBuilders/wiki) for more information about text builders.

## Text providers
A text provider is a way to give the text for text components.
It is how you use text builders without having to define them in text components at all!
```json
{
	"text": {
		"type": "minecraft:builder",
		"builder": "namespace:path/to/builder"
	},
	"color": "red"
}
```
