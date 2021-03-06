/*
 * Created by Talab Omar.
 * Copyright (c) 2017. All rights reserved.
 */

package com.codertal.studybook.data.classes;

import com.codertal.studybook.data.classes.ClassInfo;
import com.codertal.studybook.data.teachers.Teacher;

import java.util.List;

import javax.inject.Inject;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.reactivex.Completable;
import io.reactivex.Single;

public class ClassesRepository {

    private final Box<ClassInfo> classInfoBox;

    @Inject
    public ClassesRepository(BoxStore boxStore){
        classInfoBox = boxStore.boxFor(ClassInfo.class);
    }

    public Completable save(ClassInfo entity) {
        return Completable.fromAction(() -> classInfoBox.put(entity));
    }

    public Single<List<ClassInfo>> getAllClasses() {
        return Single.fromCallable(classInfoBox::getAll);
    }

    public Single<ClassInfo> getClassInfo(long classId) {
        return Single.fromCallable(() -> classInfoBox.get(classId));
    }

    public long getClassTeacherId(ClassInfo classInfo) {
        return classInfo.getTeacher().getTargetId();
    }

    public void assignTeacherToClass(ClassInfo classInfo, Teacher teacher) {
        classInfo.getTeacher().setTarget(teacher);
    }

    public boolean classHasTeacher(ClassInfo classInfo) {
        return !classInfo.getTeacher().isNull();
    }

}
