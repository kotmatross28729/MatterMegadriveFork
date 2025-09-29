package matteroverdrive.client.render.utils;

import net.minecraftforge.client.model.IModelCustom;

import java.util.List;

/*
* Taken from: https://github.com/HbmMods/Hbm-s-Nuclear-Tech-GIT/blob/master/src/main/java/com/hbm/render/loader/IModelCustomNamed.java
*/
public interface IModelCustomNamed extends IModelCustom {
	public List<String> getPartNames();
}
