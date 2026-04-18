# Instructions

- Following Playwright test failed.
- Explain why, be concise, respect Playwright best practices.
- Provide a snippet of code with the fix, if possible.

# Test info

- Name: example.spec.ts >> click button should call backend and display result
- Location: tests\example.spec.ts:3:5

# Error details

```
Error: expect(locator).toContainText(expected) failed

Locator: locator('#result')
Expected substring: "Romain"
Timeout: 5000ms
Error: element(s) not found

Call log:
  - Expect "toContainText" with timeout 5000ms
  - waiting for locator('#result')

```

# Page snapshot

```yaml
- generic [ref=e2]:
  - heading "LoL Stats Dashboard" [level=1] [ref=e3]
  - paragraph [ref=e4]: Projet personnel de stats de games League of Legends
  - generic [ref=e6]:
    - heading "Page des matchs" [level=2] [ref=e7]
    - paragraph [ref=e8]: liste des matchs aa
    - textbox [ref=e9]: fawulsCpzlkgkfMcs1FW2JKvhE9HR1OnTmv8nsztXLQdtnrYWdJS1Y_Q9AnH8q6RSyJZhF71EZczXA
    - button "Tester GET" [active] [ref=e10]
```

# Test source

```ts
  1  | import { test, expect } from '@playwright/test';
  2  | 
  3  | test('click button should call backend and display result', async ({ page }) => {
  4  |   await page.goto('/');
  5  | 
  6  |   await page.fill(
  7  |     'input',
  8  |     'fawulsCpzlkgkfMcs1FW2JKvhE9HR1OnTmv8nsztXLQdtnrYWdJS1Y_Q9AnH8q6RSyJZhF71EZczXA',
  9  |   );
  10 |   // clique sur le bouton
  11 |   await page.click('button');
  12 | 
  13 |   // vérifie que le résultat apparait
> 14 |   await expect(page.locator('#result')).toContainText('Romain');
     |                                         ^ Error: expect(locator).toContainText(expected) failed
  15 | });
  16 | 
```