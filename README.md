# NisabGold — Gold Zakat Calculator

A minimalist Android app that calculates zakat on gold, with a step-by-step breakdown so users can verify every number themselves.

Built with **Java** and **Android Studio** for the Mobile Technology course at Universiti Teknologi MARA (UiTM).

---

## Features

- **Three inputs:** gold weight (grams), gold type (Savings or Worn), and current gold price (RM per gram)
- **Automatic uruf handling:** applies the correct exempted weight for each gold type
- **Clear results:** total gold value, zakat-payable amount, and the final zakat due
- **"How this was calculated" breakdown:** a collapsible 4-step walkthrough using the user's own numbers
- **Live input validation:** the Calculate button stays disabled until the inputs are valid
- **Educational info dialog:** explains what *uruf* means, right where the user needs it
- **Share:** send the app link through any installed app
- **About page:** developer details, app version, and a clickable link to this repository
- **Custom design:** dark gold theme, custom title bar, and custom app icon

---

## How zakat is calculated

| Gold type | Uruf (exempted weight) |
|---|---|
| Savings | 85 g |
| Worn | 200 g |

```
Total value      = weight × price per gram
Zakatable weight = weight − uruf          (0 if weight ≤ uruf)
Zakatable value  = zakatable weight × price per gram
Zakat due        = zakatable value × 2.5%
```

**Example:** 100 g of Savings gold at RM280/g

1. Total value = 100 × 280 = **RM28,000.00**
2. Zakatable weight = 100 − 85 = **15 g**
3. Zakatable value = 15 × 280 = **RM4,200.00**
4. Zakat due = 4,200 × 2.5% = **RM105.00**

If the weight does not exceed the uruf, the app shows RM0.00 and explains why.

> This app is a calculation aid. For rulings specific to your situation, please consult your local zakat authority or a qualified religious scholar.

---

## Design decisions (Human-Computer Interaction)

| Principle | How it is applied |
|---|---|
| Error prevention | Calculate button is disabled until inputs are valid |
| Recognition over recall | Savings / Worn toggle shows both options at once instead of a hidden dropdown |
| Progressive disclosure | Calculation steps stay collapsed until the user asks for them |
| Visual hierarchy | The zakat amount is the largest element on the result card |
| Feedback | Results fade in smoothly after calculating |
| Help where it is needed | Inline hints under fields and an info dialog for *uruf* |
| Accessibility | Content descriptions on icons and high-contrast text on a dark background |

---

## Tech stack

- **Language:** Java
- **IDE:** Android Studio
- **UI:** XML layouts, Material Components
- **Min SDK:** API 24 (Android 7.0)
- **Version control:** Git & GitHub

---

## Project structure

```
app/src/main/
├── java/com/example/nisabgold/
│   ├── MainActivity.java      # Input handling, validation, calculation, breakdown
│   └── AboutActivity.java     # About screen
├── res/
│   ├── layout/
│   │   ├── activity_main.xml
│   │   └── activity_about.xml
│   ├── drawable/              # Backgrounds, toggle states, icons
│   ├── mipmap-*/              # App launcher icon
│   └── values/                # Colors, strings, themes
└── AndroidManifest.xml
```

---

## Getting started

1. Clone the repository
   ```bash
   git clone https://github.com/solehahsaufi/NisabGold.git
   ```
2. Open the project in **Android Studio**
3. Let Gradle sync finish
4. Run the app on an emulator or a physical Android device (API 24+)

---

## What I learned

- Structuring an Android app across multiple activities in Java
- Handling live input validation with `TextWatcher`
- Building custom UI components (toggle, collapsible card, custom title bar) without relying on default widgets
- Applying HCI principles to make a financial tool clear and trustworthy
- Managing a project with Git and GitHub

---

## Author

**Solehah**
Bachelor of Information Technology (Hons.), Universiti Teknologi MARA Cawangan Kelantan (UiTMCK)

- GitHub: [@solehahsaufi](https://github.com/solehahsaufi)
- LinkedIn: [ https://www.linkedin.com/in/solehah-saufi-952331288  ]
- Email: [ 2024907823@student.uitm.edu.my ]
