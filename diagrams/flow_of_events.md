# Вход и регистрация в приложении

**Акторы**: Пользователь, Система  
**Цель**: Войти в существующий аккаунт или зарегистрироваться в приложении  
**Предусловия**: Пользователь установил приложение и открыл его

## Основной поток:
1. Пользователь открывает приложение.
2. Система отображает форму входа.
3. Пользователь выбирает, есть ли у него аккаунт:
    - Если аккаунт есть, пользователь вводит логин и пароль.
    - Если аккаунта нет, пользователь открывает форму регистрации.
4. Система проверяет корректность введённых данных:
    - Если данные введены правильно, система предоставляет доступ к аккаунту.
5. Пользователь успешно входит в аккаунт.

## Альтернативный поток (Регистрация):
1. На шаге 3, если у пользователя нет аккаунта, он открывает форму регистрации.
2. Пользователь вводит необходимые данные (например, имя, адрес электронной почты, пароль).
3. Пользователь нажимает кнопку "Зарегистрироваться".
4. Система проверяет корректность введённых данных:
    - Если данные корректны, система регистрирует пользователя.
    - Если данные некорректны, система уведомляет пользователя об ошибке и предлагает исправить данные.
5. После успешной регистрации пользователь автоматически входит в аккаунт.

## Альтернативный поток (Ошибка входа):
1. На шаге 4, если данные введены некорректно (например, неверный пароль):
    - Система уведомляет пользователя о неверных данных и предлагает повторить попытку ввода.

---

# Запуск и прохождение игры в приложении "Солитер"

**Акторы**: Пользователь приложения, Система  
**Цель**: Запустить игру и сыграть партию в солитёр  
**Предусловия**: Пользователь установил и открыл приложение "Солитер"

## Основной поток:
1. Пользователь открывает приложение "Солитер".
2. Пользователь переходит на главную страницу.
3. Пользователь запускает игру жестом/нажатием на кнопку.
4. Пользователь передвигает карты:
    - Если не осталось доступных действий, игра завершается
5. Пользователь выбирает:
    - нажать на кнопку "+", чтобы запустить новую игру
    - выйти в меню
