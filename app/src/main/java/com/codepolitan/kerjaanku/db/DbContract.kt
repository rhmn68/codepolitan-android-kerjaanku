package com.codepolitan.kerjaanku.db

object DbContract {
    class MyTask {
        companion object{
            const val TABLE_TASK = "task"
            const val TASK_ID = "task_id"
            const val TASK_TITLE = "task_title"
            const val TASK_DETAILS = "task_details"
            const val TASK_DATE = "task_date"
            const val TASK_IS_COMPLETE = "task_is_complete"
        }
    }

    class MySubTask {
        companion object{
            const val TABLE_SUB_TASK = "sub_task"
            const val SUB_TASK_ID = "sub_task_id"
            const val SUB_TASK_TASK_ID = "sub_task_task_id"
            const val SUB_TASK_TITLE = "sub_task_title"
            const val SUB_TASK_IS_COMPLETE = "sub_task_is_complete"
        }
    }

    class CompleteMyTask {
        companion object{
            const val TABLE_COMPLETE_TASK = "complete_task"
            const val COMPLETE_TASK_ID = "complete_task_id"
            const val COMPLETE_TASK_TITLE = "complete_task_title"
            const val COMPLETE_TASK_DETAILS = "complete_task_details"
            const val COMPLETE_TASK_DATE = "complete_task_date"
            const val COMPLETE_TASK_IS_COMPLETE = "complete_task_is_complete"
        }
    }

    class CompleteMySubTask {
        companion object{
            const val TABLE_COMPLETE_SUB_TASK = "complete_sub_task"
            const val COMPLETE_SUB_TASK_ID = "complete_sub_task_id"
            const val COMPLETE_SUB_TASK_TASK_ID = "complete_sub_task_task_id"
            const val COMPLETE_SUB_TASK_TITLE = "complete_sub_task_title"
            const val COMPLETE_SUB_TASK_IS_COMPLETE = "complete_sub_task_is_complete"
        }
    }
}