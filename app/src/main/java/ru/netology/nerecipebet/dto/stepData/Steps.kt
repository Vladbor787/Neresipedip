package ru.netology.nerecipebet.dto.stepData

internal fun StepEntity.toStep() = Step(
    idStep = idStep,
    idRecipe = idRecipe,
    stepText = stepText,
    picture = picture
)

internal fun Step.toEntity() = StepEntity(
    idStep = idStep,
    idRecipe = idRecipe,
    stepText = stepText,
    picture = picture
)