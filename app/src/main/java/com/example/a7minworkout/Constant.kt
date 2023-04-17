package com.example.a7minworkout

object Constant {

    fun defaultExerciseList(): ArrayList<ExerciseModel> {

        val exerciseList = ArrayList<ExerciseModel>()

        val jumpingJacks =
            ExerciseModel(1, "Jumping Jacks", R.drawable.ic_jumping_jacks, false, false)
        exerciseList.add(jumpingJacks)


        val pushUp = ExerciseModel(2, "Push Up", R.drawable.ic_push_up, false, false)
        exerciseList.add(pushUp)

        val abdominalCrunch =
            ExerciseModel(3, "Abdominal Crunch", R.drawable.ic_abdominal_crunch, false, false)
        exerciseList.add(abdominalCrunch)


        val squat = ExerciseModel(4, "Squat", R.drawable.ic_squat, false, false)
        exerciseList.add(squat)



        val highKneesRunningInPlace =
            ExerciseModel(
                5, "High Knees Running In Place",
                R.drawable.ic_high_knees_running_in_place,
                false,
                false
            )
        exerciseList.add(highKneesRunningInPlace)


        val pushupAndRotation =
            ExerciseModel(
                6,
                "Push up and Rotation",
                R.drawable.ic_push_up_and_rotation,
                false,
                false
            )
        exerciseList.add(pushupAndRotation)


        return exerciseList
    }
}