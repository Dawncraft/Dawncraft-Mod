package io.github.dawncraft.client.renderer.skill;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.io.Reader;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Map.Entry;

public class ModelSkill
{
    static final Gson SERIALIZER = new GsonBuilder().registerTypeAdapter(ModelSkill.class, new Deserializer()).create();
    public String name;
    public final Map<Integer, String> textures;

    public ModelSkill(Map<Integer, String> textures)
    {
        this.name = "";
        this.textures = textures;
    }

    public String resolveTextureName(String textureName)
    {
        if (!textureName.startsWith("#"))
        {
            textureName = '#' + textureName;
        }
        int level = Integer.valueOf(textureName.substring(1));
        while (level > 0 && !this.textures.containsKey(level))
        {
            --level;
        }
        String texture = (String) this.textures.get(level);
        return texture != null && !texture.startsWith("#") ? texture : "missingno";
    }

    public static ModelSkill deserialize(Reader reader)
    {
        return (ModelSkill) SERIALIZER.fromJson(reader, ModelSkill.class);
    }
    
    public static ModelSkill deserialize(String jsonString)
    {
        return deserialize(new StringReader(jsonString));
    }
    
    @SideOnly(Side.CLIENT)
    public static class Deserializer implements JsonDeserializer<ModelSkill>
    {
        @Override
        public ModelSkill deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
        {
            JsonObject jsonSkill = json.getAsJsonObject();
            Map<Integer, String> textures = this.getTextures(jsonSkill);
            return new ModelSkill(textures);
        }
        
        private Map<Integer, String> getTextures(JsonObject jsonSkill)
        {
            Map<Integer, String> map = Maps.newHashMap();

            if (jsonSkill.has("textures"))
            {
                JsonObject jsonTextures = jsonSkill.getAsJsonObject("textures");

                for (Entry<String, JsonElement> entry : jsonTextures.entrySet())
                {
                    map.put(Integer.valueOf(entry.getKey()), ((JsonElement) entry.getValue()).getAsString());
                }
            }

            return map;
        }
    }
}
