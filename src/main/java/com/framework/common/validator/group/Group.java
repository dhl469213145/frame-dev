/**
 * Copyright (c) 2016-2019 湖南鼎翰 All rights reserved.
 *
 * https://www.sqqmall.io
 *
 * 版权所有，侵权必究！
 */

package com.framework.common.validator.group;

import javax.validation.GroupSequence;

/**
 * 定义校验顺序，如果AddGroup组失败，则UpdateGroup组不会再校验
 *
 * @author Mark WenJunChi
 */
@GroupSequence({AddGroup.class, UpdateGroup.class})
public interface Group {

}
