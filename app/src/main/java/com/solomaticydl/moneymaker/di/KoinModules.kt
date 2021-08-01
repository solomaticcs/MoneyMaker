package com.solomaticydl.moneymaker.di

import android.app.Application
import com.solomaticydl.moneymaker.db.AppDatabase
import com.solomaticydl.moneymaker.db.dao.RecordDao
import com.solomaticydl.moneymaker.repository.AdditionRepository
import com.solomaticydl.moneymaker.repository.AdditionRepositoryImpl
import com.solomaticydl.moneymaker.repository.RecordListRepository
import com.solomaticydl.moneymaker.repository.RecordListRepositoryImpl
import com.solomaticydl.moneymaker.ui.addition.AdditionViewModel
import com.solomaticydl.moneymaker.ui.main.RecordListViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

val databaseModule = module {
    fun provideDatabase(application: Application): AppDatabase {
        return AppDatabase.getDatabase(application)
    }

    fun provideRecordDao(database: AppDatabase): RecordDao {
        return database.recordDao()
    }

    single { provideDatabase(androidApplication()) }
    single { provideRecordDao(get()) }
}

val appModule = module {
    loadKoinModules(databaseModule)
    single<RecordListRepository> { RecordListRepositoryImpl(get()) }
    viewModel { RecordListViewModel(get()) }
    single<AdditionRepository> { AdditionRepositoryImpl(get()) }
    viewModel { AdditionViewModel(get()) }
}
