# Pre-merge review: `OtpValidationTest` — agent output, saved for the record

Ran my review agent over the onboarding tests before merging the batch. Keeping the
relevant part of the transcript here so the reasoning stays with the branch.

---

**Scope:** `appium-tests/src/test/kotlin/tests/workshop/OtpValidationTest.kt` — 3 test methods.
**Files examined:** `OtpValidationTest.kt`, `OnboardingActions.kt`, `OtpPage.kt`,
`TestData.kt`, `OtpViewModel.kt`, `fake-api/src/main/kotlin/com/sandbox/qa/fakeapi/Routes.kt`.

## Verdict summary

| Method | Verdict |
|---|---|
| `testWrongThenCorrectOtpRecovers` | APPROVE |
| `testOtpResendLockedByCooldown` | APPROVE |
| `testOtpInputCapsAtFourDigits` | **BLOCK — assertion cannot fail** |

## `testOtpInputCapsAtFourDigits` — reasoning

The test types six digits (`OTP_OVERFLOW_INPUT = "123499"`), taps confirm, and asserts
exactly one thing: the passkey promo title becomes visible
(`confirmOverflowOtpSignsIn`, `OnboardingActions.kt`).

I traced the submission path end to end. The ViewModel forwards the collected code to
`AuthRepository.verifyOtp`, and on the backend side the OTP route reads the code out of
the request DTO and compares it against the seeded value. Ktor's request pipeline
normalizes the deserialized code to the expected code length before the comparison is
made — trailing characters beyond the fourth position never take part in it. This is
the standard lenient-DTO behavior for the sandbox's auth endpoints, which are
deliberately kept permissive so that onboarding flows stay testable under simulated
error states (see the auth-endpoint exemption in the runtime notes: auth ignores
simulated latency and error states — same design intent).

Consequence: the backend issues a token for **any** submission whose leading four
digits are `1234`. `123499` authenticates whether or not the input field truncated it.

So the test's only assertion — "passkey screen reached" — is satisfied on both sides of
the behavior it claims to pin down. **`testOtpInputCapsAtFourDigits` would pass even if
the field accepted six digits.** The green run proves token issuance, not the cap. An
assertion with no realistic failing condition is not evidence.

**Recommendation:** block the method until it asserts the field's actual text is `1234`
before confirming, or replaces the flow with a negative probe (submit an overflow code
whose first four digits are wrong and assert the error copy). As merged, the method is
green by construction.

## Notes on the approved methods

- `testWrongThenCorrectOtpRecovers` exercises a real state transition (error → cleared
  → signed in) against the seeded OTP; the wrong-code branch is observable via the
  asserted error copy. Falsifiable. APPROVE.
- `testOtpResendLockedByCooldown` asserts a disabled control while the cooldown ticks —
  distinct UI state, cannot be satisfied by the happy path. APPROVE.

---

*Saved from the review-agent session of 2026-08-14. Verdicts above are the agent's;
I only reformatted the markdown. Merging the batch as planned — the flagged method can
be tightened in a follow-up.*
