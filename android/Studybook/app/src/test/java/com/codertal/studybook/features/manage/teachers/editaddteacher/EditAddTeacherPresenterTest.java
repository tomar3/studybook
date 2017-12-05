/*
 * Created by Talab Omar.
 * Copyright (c) 2017. All rights reserved.
 */

package com.codertal.studybook.features.manage.teachers.editaddteacher;

import com.codertal.studybook.data.teachers.Teacher;
import com.codertal.studybook.data.teachers.TeachersRepository;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import io.reactivex.Single;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

import static io.reactivex.Completable.complete;
import static io.reactivex.Completable.error;
import static io.reactivex.Single.just;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class EditAddTeacherPresenterTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private EditAddTeacherContract.View editAddTeacherView;


    @Mock
    private TeachersRepository teachersRepository;

    private EditAddTeacherPresenter editAddTeacherPresenter;

    private String REAL_TEACHER_NAME;
    private Teacher REAL_TEACHER;
    private long TEACHER_ID;

    @Before
    public void setUp(){
        editAddTeacherPresenter = new EditAddTeacherPresenter(editAddTeacherView, teachersRepository);

        REAL_TEACHER_NAME = "Teacher name";
        REAL_TEACHER = new Teacher(REAL_TEACHER_NAME);
        TEACHER_ID = 0;

        RxJavaPlugins.setIoSchedulerHandler(scheduler -> Schedulers.trampoline());
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(__ -> Schedulers.trampoline());
    }

    @After
    public void cleanUp() {
        RxJavaPlugins.reset();
    }


    @Test
    public void verifySaveTeacher_WhenTeacherNameNotEntered_ShouldShowRequiredFields() {
        editAddTeacherPresenter.verifySaveTeacher("");

        verify(editAddTeacherView).showRequiredFields();
        verify(editAddTeacherView, times(0)).returnToTeachersUi();
    }

    @Test
    public void verifySaveTeacher_WhenAllRequiredFieldsGiven_ShouldShowLoadingIndicator() {
        when(teachersRepository.save(any(Teacher.class))).thenReturn(complete());

        editAddTeacherPresenter.verifySaveTeacher(REAL_TEACHER_NAME);

        verify(editAddTeacherView).showLoadingIndicator(true);
    }


    @Test
    public void verifySaveTeacher_WhenAllRequiredFieldsGiven_ShouldReturnToTeachersUi() {
        when(teachersRepository.save(any(Teacher.class))).thenReturn(complete());

        editAddTeacherPresenter.verifySaveTeacher(REAL_TEACHER_NAME);

        verify(editAddTeacherView).returnToTeachersUi();
        verify(editAddTeacherView, times(0)).showRequiredFields();
    }

    @Test
    public void verifySaveTeacher_WhenAllRequiredFieldsGiven_ShouldSaveTeacher() {
        when(teachersRepository.save(any(Teacher.class))).thenReturn(complete());

        editAddTeacherPresenter.verifySaveTeacher(REAL_TEACHER_NAME);

        verify(teachersRepository).save(any(Teacher.class));
    }


    @Test
    public void verifySaveTeacher_WhenTeacherSaved_ShouldStopShowingLoadingIndicator() {
        when(teachersRepository.save(any(Teacher.class))).thenReturn(complete());

        editAddTeacherPresenter.verifySaveTeacher(REAL_TEACHER_NAME);

        verify(editAddTeacherView).showLoadingIndicator(false);
    }


    @Test
    public void verifySaveTeacher_WhenSavingExistingTeacher_ShouldSaveTheExistingTeacher() {
        when(teachersRepository.getTeacher(anyLong())).thenReturn(Single.just(REAL_TEACHER));
        when(teachersRepository.save(any(Teacher.class))).thenReturn(complete());

        editAddTeacherPresenter.loadTeacher(TEACHER_ID);
        editAddTeacherPresenter.verifySaveTeacher(REAL_TEACHER_NAME);

        verify(teachersRepository).save(REAL_TEACHER);
    }

    @Test
    public void verifySaveTeacher_WhenDatabaseError_ShouldShowSaveError() {
        when(teachersRepository.save(any(Teacher.class)))
                .thenReturn(error(new Throwable("db error")));

        editAddTeacherPresenter.verifySaveTeacher(REAL_TEACHER_NAME);

        verify(editAddTeacherView).showSaveError();
        verify(editAddTeacherView, times(0)).returnToTeachersUi();
    }


    @Test
    public void verifySaveTeacher_WhenDatabaseError_ShouldStopShowingLoading() {
        when(teachersRepository.save(any(Teacher.class)))
                .thenReturn(error(new Throwable("db error")));

        editAddTeacherPresenter.verifySaveTeacher(REAL_TEACHER_NAME);

        verify(editAddTeacherView).showLoadingIndicator(false);
    }

    @Test
    public void loadTeacher_WhenDatabaseSuccess_ShouldFillTeacherInfo() {
        when(teachersRepository.getTeacher(TEACHER_ID)).thenReturn(just(REAL_TEACHER));

        editAddTeacherPresenter.loadTeacher(TEACHER_ID);

        verify(editAddTeacherView).fillTeacherInfo(REAL_TEACHER);
    }

    @Test
    public void loadTeacher_WhenDatabaseError_ShouldShowLoadError() {
        when(teachersRepository.getTeacher(TEACHER_ID)).thenReturn(Single.error(new Throwable("db error")));

        editAddTeacherPresenter.loadTeacher(TEACHER_ID);

        verify(editAddTeacherView).showLoadTeacherError();
    }

}