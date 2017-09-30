package com.cmos.edccommon.beans.jsr303demo;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import com.cmos.common.bean.GenericBean;
/**
 * 请描述该类的功能描述
 * @author likerui
 * @date2016年11月22日上午9:55:33
 */
public class CompModelTwo extends GenericBean{

	@Valid
	@Size(min = 1, max = 2)
	@NotNull
	UserModel[] userModels;
	
	@Valid @NotNull
	MyModel myModel;

	public UserModel[] getUserModels() {
		return userModels;
	}

	public void setUserModels(UserModel[] userModels) {
		this.userModels = userModels;
	}

	public MyModel getMyModel() {
		return myModel;
	}

	public void setMyModel(MyModel myModel) {
		this.myModel = myModel;
	}
}
