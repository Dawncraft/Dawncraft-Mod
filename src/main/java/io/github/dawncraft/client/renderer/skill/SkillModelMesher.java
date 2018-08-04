package io.github.dawncraft.client.renderer.skill;

import java.util.IdentityHashMap;
import java.util.Map;
import com.google.common.collect.Maps;

import gnu.trove.map.hash.TIntObjectHashMap;
import gnu.trove.procedure.TIntObjectProcedure;
import io.github.dawncraft.client.renderer.model.SkillMeshDefinition;
import io.github.dawncraft.skill.Skill;
import io.github.dawncraft.skill.SkillStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;

public class SkillModelMesher
{
    private final IdentityHashMap<Skill, TIntObjectHashMap<ModelResourceLocation>> locations = Maps.newIdentityHashMap();
    private final IdentityHashMap<Skill, TIntObjectHashMap<IBakedModel>> models = Maps.newIdentityHashMap();
    private final Map<Skill, SkillMeshDefinition> shapers = Maps.<Skill, SkillMeshDefinition>newHashMap();
    private final ModelManager modelManager;
    
    public SkillModelMesher()
    {
        this.modelManager = Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getModelManager();
    }
    
    public TextureAtlasSprite getParticleIcon(Skill skill)
    {
        return this.getParticleIcon(skill, 1);
    }
    
    public TextureAtlasSprite getParticleIcon(Skill skill, int level)
    {
        return this.getSkillModel(new SkillStack(skill, level)).getParticleTexture();
    }
    
    public IBakedModel getSkillModel(SkillStack stack)
    {
        Skill skill = stack.getSkill();
        IBakedModel ibakedmodel = this.getSkillModel(skill, this.getLevel(stack));
        
        if (ibakedmodel == null)
        {
            SkillMeshDefinition skillmeshdefinition = (SkillMeshDefinition)this.shapers.get(skill);
            
            if (skillmeshdefinition != null)
            {
                ibakedmodel = this.modelManager.getModel(skillmeshdefinition.getModelLocation(stack));
            }
        }
        
        if (ibakedmodel == null)
        {
            ibakedmodel = this.modelManager.getMissingModel();
        }
        
        return ibakedmodel;
    }
    
    protected int getLevel(SkillStack stack)
    {
        return stack.getSkillLevel();
    }
    
    protected IBakedModel getSkillModel(Skill skill, int level)
    {
        TIntObjectHashMap<IBakedModel> map = this.models.get(skill);
        return map == null ? null : map.get(level);
    }
    
    public void register(Skill skill, int level, ModelResourceLocation location)
    {
        TIntObjectHashMap<ModelResourceLocation> locs = this.locations.get(skill);
        TIntObjectHashMap<IBakedModel>           mods = this.models.get(skill);
        if (locs == null)
        {
            locs = new TIntObjectHashMap<ModelResourceLocation>();
            this.locations.put(skill, locs);
        }
        if (mods == null)
        {
            mods = new TIntObjectHashMap<IBakedModel>();
            this.models.put(skill, mods);
        }
        locs.put(level, location);
        mods.put(level, this.getModelManager().getModel(location));
    }
    
    public void register(Skill skill, SkillMeshDefinition definition)
    {
        this.shapers.put(skill, definition);
    }
    
    public void rebuildCache()
    {
        final ModelManager manager = this.getModelManager();
        for (Map.Entry<Skill, TIntObjectHashMap<ModelResourceLocation>> e : this.locations.entrySet())
        {
            TIntObjectHashMap<IBakedModel> mods = this.models.get(e.getKey());
            if (mods != null)
            {
                mods.clear();
            }
            else
            {
                mods = new TIntObjectHashMap<IBakedModel>();
                this.models.put(e.getKey(), mods);
            }
            final TIntObjectHashMap<IBakedModel> map = mods;
            e.getValue().forEachEntry(new TIntObjectProcedure<ModelResourceLocation>()
            {
                @Override
                public boolean execute(int level, ModelResourceLocation location)
                {
                    map.put(level, manager.getModel(location));
                    return true;
                }
            });
        }
    }

    public ModelManager getModelManager()
    {
        return this.modelManager;
    }
}
