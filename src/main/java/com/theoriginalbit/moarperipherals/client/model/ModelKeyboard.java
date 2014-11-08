/**
 * Copyright 2014 Joshua Asbury (@theoriginalbit)
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
package com.theoriginalbit.moarperipherals.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelKeyboard extends ModelBase {

    protected ModelRenderer base, stand, key1, key2, key3, key4, key5, key6, key7, key8,
            key9, key10, key11, key12, key13, key14, key15, key16, key17,
            key18, key19, key20, key21, key22, key23, key24, key25, key26,
            key27, key28, key29, key30, key31, key32, key33, key34, key35;

    public ModelKeyboard() {
        textureWidth = 128;
        textureHeight = 32;

        base = new ModelRenderer(this, 0, 0);
        base.addBox(-15.5F, 0F, -7F, 30, 1, 13);
        base.setRotationPoint(0F, 0F, 0F);
        base.setTextureSize(128, 32);
        base.mirror = true;
        setRotation(base, 0.1396263F, 0F, 0F);
        stand = new ModelRenderer(this, 0, 19);
        stand.addBox(-15.5F, 0F, -1F, 30, 2, 2);
        stand.setRotationPoint(0F, 0F, 5F);
        stand.setTextureSize(128, 32);
        stand.mirror = true;
        setRotation(stand, 0F, 0F, 0F);
        key1 = new ModelRenderer(this, 0, 0);
        key1.addBox(-1F, 0F, -1F, 2, 1, 2);
        key1.setRotationPoint(-14F, -1F, 4F);
        key1.setTextureSize(128, 32);
        key1.mirror = true;
        setRotation(key1, 0.1396263F, 0F, 0F);
        key2 = new ModelRenderer(this, 0, 0);
        key2.addBox(-1F, 0F, -1F, 2, 1, 2);
        key2.setRotationPoint(-11F, -1F, 4F);
        key2.setTextureSize(128, 32);
        key2.mirror = true;
        setRotation(key2, 0.1396263F, 0F, 0F);
        key3 = new ModelRenderer(this, 0, 0);
        key3.addBox(-1F, 0F, -1F, 2, 1, 2);
        key3.setRotationPoint(-8F, -1F, 4F);
        key3.setTextureSize(128, 32);
        key3.mirror = true;
        setRotation(key3, 0.1396263F, 0F, 0F);
        key4 = new ModelRenderer(this, 0, 0);
        key4.addBox(-1F, 0F, -1F, 2, 1, 2);
        key4.setRotationPoint(-5F, -1F, 4F);
        key4.setTextureSize(128, 32);
        key4.mirror = true;
        setRotation(key4, 0.1396263F, 0F, 0F);
        key5 = new ModelRenderer(this, 0, 0);
        key5.addBox(-1F, 0F, -1F, 2, 1, 2);
        key5.setRotationPoint(-2F, -1F, 4F);
        key5.setTextureSize(128, 32);
        key5.mirror = true;
        setRotation(key5, 0.1396263F, 0F, 0F);
        key6 = new ModelRenderer(this, 0, 0);
        key6.addBox(-1F, 0F, -1F, 2, 1, 2);
        key6.setRotationPoint(1F, -1F, 4F);
        key6.setTextureSize(128, 32);
        key6.mirror = true;
        setRotation(key6, 0.1396263F, 0F, 0F);
        key7 = new ModelRenderer(this, 0, 0);
        key7.addBox(-1F, 0F, -1F, 2, 1, 2);
        key7.setRotationPoint(4F, -1F, 4F);
        key7.setTextureSize(128, 32);
        key7.mirror = true;
        setRotation(key7, 0.1396263F, 0F, 0F);
        key8 = new ModelRenderer(this, 0, 0);
        key8.addBox(-1F, 0F, -1F, 2, 1, 2);
        key8.setRotationPoint(7F, -1F, 4F);
        key8.setTextureSize(128, 32);
        key8.mirror = true;
        setRotation(key8, 0.1396263F, 0F, 0F);
        key9 = new ModelRenderer(this, 0, 0);
        key9.addBox(-1F, 0F, -1F, 2, 1, 2);
        key9.setRotationPoint(10F, -1F, 4F);
        key9.setTextureSize(128, 32);
        key9.mirror = true;
        setRotation(key9, 0.1396263F, 0F, 0F);
        key10 = new ModelRenderer(this, 0, 0);
        key10.addBox(-1F, 0F, -1F, 2, 1, 2);
        key10.setRotationPoint(13F, -1F, 4F);
        key10.setTextureSize(128, 32);
        key10.mirror = true;
        setRotation(key10, 0.1396263F, 0F, 0F);
        key11 = new ModelRenderer(this, 0, 0);
        key11.addBox(-1F, 0F, -1F, 2, 1, 2);
        key11.setRotationPoint(-14F, -0.6F, 1F);
        key11.setTextureSize(128, 32);
        key11.mirror = true;
        setRotation(key11, 0.1396263F, 0F, 0F);
        key12 = new ModelRenderer(this, 0, 0);
        key12.addBox(-1F, 0F, -1F, 2, 1, 2);
        key12.setRotationPoint(-11F, -0.6F, 1F);
        key12.setTextureSize(128, 32);
        key12.mirror = true;
        setRotation(key12, 0.1396263F, 0F, 0F);
        key13 = new ModelRenderer(this, 0, 0);
        key13.addBox(-1F, 0F, -1F, 2, 1, 2);
        key13.setRotationPoint(-8F, -0.6F, 1F);
        key13.setTextureSize(128, 32);
        key13.mirror = true;
        setRotation(key13, 0.1396263F, 0F, 0F);
        key14 = new ModelRenderer(this, 0, 0);
        key14.addBox(-1F, 0F, -1F, 2, 1, 2);
        key14.setRotationPoint(-5F, -0.6F, 1F);
        key14.setTextureSize(128, 32);
        key14.mirror = true;
        setRotation(key14, 0.1396263F, 0F, 0F);
        key15 = new ModelRenderer(this, 0, 0);
        key15.addBox(-1F, 0F, -1F, 2, 1, 2);
        key15.setRotationPoint(-2F, -0.6F, 1F);
        key15.setTextureSize(128, 32);
        key15.mirror = true;
        setRotation(key15, 0.1396263F, 0F, 0F);
        key16 = new ModelRenderer(this, 0, 0);
        key16.addBox(-1F, 0F, -1F, 2, 1, 2);
        key16.setRotationPoint(1F, -0.6F, 1F);
        key16.setTextureSize(128, 32);
        key16.mirror = true;
        setRotation(key16, 0.1396263F, 0F, 0F);
        key17 = new ModelRenderer(this, 0, 0);
        key17.addBox(-1F, 0F, -1F, 2, 1, 2);
        key17.setRotationPoint(4F, -0.6F, 1F);
        key17.setTextureSize(128, 32);
        key17.mirror = true;
        setRotation(key17, 0.1396263F, 0F, 0F);
        key18 = new ModelRenderer(this, 0, 0);
        key18.addBox(-1F, 0F, -1F, 2, 1, 2);
        key18.setRotationPoint(7F, -0.6F, 1F);
        key18.setTextureSize(128, 32);
        key18.mirror = true;
        setRotation(key18, 0.1396263F, 0F, 0F);
        key19 = new ModelRenderer(this, 0, 0);
        key19.addBox(-1F, 0F, -1F, 2, 1, 2);
        key19.setRotationPoint(10F, -0.6F, 1F);
        key19.setTextureSize(128, 32);
        key19.mirror = true;
        setRotation(key19, 0.1396263F, 0F, 0F);
        key20 = new ModelRenderer(this, 0, 0);
        key20.addBox(-1F, 0F, -1F, 2, 1, 2);
        key20.setRotationPoint(13F, -0.6F, 1F);
        key20.setTextureSize(128, 32);
        key20.mirror = true;
        setRotation(key20, 0.1396263F, 0F, 0F);
        key21 = new ModelRenderer(this, 0, 0);
        key21.addBox(-1F, 0F, -1F, 2, 1, 2);
        key21.setRotationPoint(-14F, -0.2F, -2F);
        key21.setTextureSize(128, 32);
        key21.mirror = true;
        setRotation(key21, 0.1396263F, 0F, 0F);
        key22 = new ModelRenderer(this, 0, 0);
        key22.addBox(-1F, 0F, -1F, 2, 1, 2);
        key22.setRotationPoint(-11F, -0.2F, -2F);
        key22.setTextureSize(128, 32);
        key22.mirror = true;
        setRotation(key22, 0.1396263F, 0F, 0F);
        key23 = new ModelRenderer(this, 0, 0);
        key23.addBox(-1F, 0F, -1F, 2, 1, 2);
        key23.setRotationPoint(-8F, -0.2F, -2F);
        key23.setTextureSize(128, 32);
        key23.mirror = true;
        setRotation(key23, 0.1396263F, 0F, 0F);
        key24 = new ModelRenderer(this, 0, 0);
        key24.addBox(-1F, 0F, -1F, 2, 1, 2);
        key24.setRotationPoint(-5F, -0.2F, -2F);
        key24.setTextureSize(128, 32);
        key24.mirror = true;
        setRotation(key24, 0.1396263F, 0F, 0F);
        key25 = new ModelRenderer(this, 0, 0);
        key25.addBox(-1F, 0F, -1F, 2, 1, 2);
        key25.setRotationPoint(-2F, -0.2F, -2F);
        key25.setTextureSize(128, 32);
        key25.mirror = true;
        setRotation(key25, 0.1396263F, 0F, 0F);
        key26 = new ModelRenderer(this, 0, 0);
        key26.addBox(-1F, 0F, -1F, 2, 1, 2);
        key26.setRotationPoint(1F, -0.2F, -2F);
        key26.setTextureSize(128, 32);
        key26.mirror = true;
        setRotation(key26, 0.1396263F, 0F, 0F);
        key27 = new ModelRenderer(this, 0, 0);
        key27.addBox(-1F, 0F, -1F, 2, 1, 2);
        key27.setRotationPoint(4F, -0.2F, -2F);
        key27.setTextureSize(128, 32);
        key27.mirror = true;
        setRotation(key27, 0.1396263F, 0F, 0F);
        key28 = new ModelRenderer(this, 0, 0);
        key28.addBox(-1F, 0F, -1F, 2, 1, 2);
        key28.setRotationPoint(7F, -0.2F, -2F);
        key28.setTextureSize(128, 32);
        key28.mirror = true;
        setRotation(key28, 0.1396263F, 0F, 0F);
        key29 = new ModelRenderer(this, 0, 0);
        key29.addBox(-1F, 0F, -1F, 2, 1, 2);
        key29.setRotationPoint(10F, -0.2F, -2F);
        key29.setTextureSize(128, 32);
        key29.mirror = true;
        setRotation(key29, 0.1396263F, 0F, 0F);
        key30 = new ModelRenderer(this, 0, 0);
        key30.addBox(-1F, 0F, -1F, 2, 1, 2);
        key30.setRotationPoint(13F, -0.2F, -2F);
        key30.setTextureSize(128, 32);
        key30.mirror = true;
        setRotation(key30, 0.1396263F, 0F, 0F);
        key31 = new ModelRenderer(this, 0, 0);
        key31.addBox(-1F, 0F, -1F, 2, 1, 2);
        key31.setRotationPoint(-14F, 0.2F, -5F);
        key31.setTextureSize(128, 32);
        key31.mirror = true;
        setRotation(key31, 0.1396263F, 0F, 0F);
        key32 = new ModelRenderer(this, 0, 15);
        key32.addBox(-2.5F, 0F, -1F, 5, 1, 2);
        key32.setRotationPoint(-9.5F, 0.2F, -5F);
        key32.setTextureSize(128, 32);
        key32.mirror = true;
        setRotation(key32, 0.1396263F, 0F, 0F);
        key33 = new ModelRenderer(this, 15, 15);
        key33.addBox(-5.5F, 0F, -1F, 11, 1, 2);
        key33.setRotationPoint(-0.5F, 0.2F, -5F);
        key33.setTextureSize(128, 32);
        key33.mirror = true;
        setRotation(key33, 0.1396263F, 0F, 0F);
        key34 = new ModelRenderer(this, 0, 15);
        key34.addBox(-2.5F, 0F, -1F, 5, 1, 2);
        key34.setRotationPoint(8.5F, 0.2F, -5F);
        key34.setTextureSize(128, 32);
        key34.mirror = true;
        setRotation(key34, 0.1396263F, 0F, 0F);
        key35 = new ModelRenderer(this, 0, 0);
        key35.addBox(-1F, 0F, -1F, 2, 1, 2);
        key35.setRotationPoint(13F, 0.2F, -5F);
        key35.setTextureSize(128, 32);
        key35.mirror = true;
        setRotation(key35, 0.1396263F, 0F, 0F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        base.render(f5);
        stand.render(f5);
        key1.render(f5);
        key2.render(f5);
        key3.render(f5);
        key4.render(f5);
        key5.render(f5);
        key6.render(f5);
        key7.render(f5);
        key8.render(f5);
        key9.render(f5);
        key10.render(f5);
        key11.render(f5);
        key12.render(f5);
        key13.render(f5);
        key14.render(f5);
        key15.render(f5);
        key16.render(f5);
        key17.render(f5);
        key18.render(f5);
        key19.render(f5);
        key20.render(f5);
        key21.render(f5);
        key22.render(f5);
        key23.render(f5);
        key24.render(f5);
        key25.render(f5);
        key26.render(f5);
        key27.render(f5);
        key28.render(f5);
        key29.render(f5);
        key30.render(f5);
        key31.render(f5);
        key32.render(f5);
        key33.render(f5);
        key34.render(f5);
        key35.render(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

}