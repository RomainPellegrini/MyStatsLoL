import { test, expect } from '@playwright/test';

test('click button should call backend and display result', async ({ page }) => {
  await page.goto('http://localhost:4200');

  await page.fill(
    'input',
    'fawulsCpzlkgkfMcs1FW2JKvhE9HR1OnTmv8nsztXLQdtnrYWdJS1Y_Q9AnH8q6RSyJZhF71EZczXA',
  );
  // clique sur le bouton
  await page.click('button');

  // vérifie que le résultat apparait
  await expect(page.locator('#result')).toContainText('Romain');
});
