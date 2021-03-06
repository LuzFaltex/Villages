/*
 * Copyright 2013 Dominic Masters and Jordan Atkins
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.domsplace.Villages.Enums;

import com.domsplace.Villages.Bases.Enum;

public class GriefType extends Enum {
    public static final GriefType INTERACT = new GriefType("Interact");
    public static final GriefType BREAK = new GriefType("Break");
    public static final GriefType PLACE = new GriefType("Place");
    public static final GriefType BLOCK_DAMAGE = new GriefType("Block Damage");
    public static final GriefType TNT_DAMAGE = new GriefType("TNT Explosion");
    
    private String type;
    
    public GriefType(String type) {
        this.type = type;
    }
    
    public String getType() {
        return this.type;
    }
}
