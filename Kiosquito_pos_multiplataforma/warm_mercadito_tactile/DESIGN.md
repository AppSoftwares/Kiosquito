---
name: Warm Mercadito Tactile
colors:
  surface: '#f9f9ff'
  surface-dim: '#cfdaf2'
  surface-bright: '#f9f9ff'
  surface-container-lowest: '#ffffff'
  surface-container-low: '#f0f3ff'
  surface-container: '#e7eeff'
  surface-container-high: '#dee8ff'
  surface-container-highest: '#d8e3fb'
  on-surface: '#111c2d'
  on-surface-variant: '#3e4a3f'
  inverse-surface: '#263143'
  inverse-on-surface: '#ecf1ff'
  outline: '#6d7a6f'
  outline-variant: '#bdcabc'
  surface-tint: '#006d3a'
  primary: '#006a39'
  on-primary: '#ffffff'
  primary-container: '#008649'
  on-primary-container: '#f6fff4'
  inverse-primary: '#64dd91'
  secondary: '#855300'
  on-secondary: '#ffffff'
  secondary-container: '#fea619'
  on-secondary-container: '#684000'
  tertiary: '#4648d4'
  on-tertiary: '#ffffff'
  tertiary-container: '#6063ee'
  on-tertiary-container: '#fffbff'
  error: '#ba1a1a'
  on-error: '#ffffff'
  error-container: '#ffdad6'
  on-error-container: '#93000a'
  primary-fixed: '#82faab'
  primary-fixed-dim: '#64dd91'
  on-primary-fixed: '#00210e'
  on-primary-fixed-variant: '#00522b'
  secondary-fixed: '#ffddb8'
  secondary-fixed-dim: '#ffb95f'
  on-secondary-fixed: '#2a1700'
  on-secondary-fixed-variant: '#653e00'
  tertiary-fixed: '#e1e0ff'
  tertiary-fixed-dim: '#c0c1ff'
  on-tertiary-fixed: '#07006c'
  on-tertiary-fixed-variant: '#2f2ebe'
  background: '#f9f9ff'
  on-background: '#111c2d'
  surface-variant: '#d8e3fb'
typography:
  display-lg:
    fontFamily: Plus Jakarta Sans
    fontSize: 36px
    fontWeight: '800'
    lineHeight: 44px
    letterSpacing: -0.03em
  display-lg-mobile:
    fontFamily: Plus Jakarta Sans
    fontSize: 30px
    fontWeight: '800'
    lineHeight: 38px
    letterSpacing: -0.02em
  headline-lg:
    fontFamily: Plus Jakarta Sans
    fontSize: 28px
    fontWeight: '700'
    lineHeight: 36px
    letterSpacing: -0.02em
  headline-md:
    fontFamily: Plus Jakarta Sans
    fontSize: 22px
    fontWeight: '700'
    lineHeight: 28px
    letterSpacing: -0.01em
  headline-sm:
    fontFamily: Plus Jakarta Sans
    fontSize: 18px
    fontWeight: '600'
    lineHeight: 24px
  body-lg:
    fontFamily: Plus Jakarta Sans
    fontSize: 16px
    fontWeight: '500'
    lineHeight: 24px
  body-md:
    fontFamily: Plus Jakarta Sans
    fontSize: 14px
    fontWeight: '400'
    lineHeight: 20px
  body-sm:
    fontFamily: Plus Jakarta Sans
    fontSize: 12px
    fontWeight: '400'
    lineHeight: 16px
  label-lg:
    fontFamily: Plus Jakarta Sans
    fontSize: 14px
    fontWeight: '700'
    lineHeight: 18px
    letterSpacing: 0.01em
  label-md:
    fontFamily: Plus Jakarta Sans
    fontSize: 12px
    fontWeight: '700'
    lineHeight: 16px
    letterSpacing: 0.02em
  label-sm:
    fontFamily: Plus Jakarta Sans
    fontSize: 10px
    fontWeight: '700'
    lineHeight: 14px
    letterSpacing: 0.04em
  currency-display:
    fontFamily: Plus Jakarta Sans
    fontSize: 32px
    fontWeight: '800'
    lineHeight: 36px
    letterSpacing: -0.02em
  currency-sub:
    fontFamily: Plus Jakarta Sans
    fontSize: 15px
    fontWeight: '600'
    lineHeight: 20px
rounded:
  sm: 0.25rem
  DEFAULT: 0.5rem
  md: 0.75rem
  lg: 1rem
  xl: 1.5rem
  full: 9999px
spacing:
  gutter: 1rem
  gutter-mobile: 0.75rem
  margin: 1.25rem
  margin-mobile: 1rem
  space-xs: 0.25rem
  space-sm: 0.5rem
  space-md: 0.75rem
  space-lg: 1.25rem
  space-xl: 1.75rem
---

## Brand & Style

This design system is crafted specifically for neighborhood grocers, bodegueros, and kiosco operators across Latin America. The brand personality balances industrious reliability with vibrant, neighborhood warmth. It rejects the cold, sterile aesthetic of corporate point-of-sale platforms in favor of a spirited, tactile, and highly legible visual language reminiscent of modern Latin American retail packaging and cheerful neighborhood signage.

The visual style blends **Modern Tactile UI** with **Warm Playful Retail Utility**:
- **Tactile Softness:** Chunky, pillowy containers with generous radiuses (rounded-2xl and rounded-3xl), substantial tap targets (minimum 48px), and gentle micro-elevations that invite quick thumb presses in busy retail settings.
- **High-Contrast Legibility:** Deep slate charcoals against warm cream canvases to ensure critical pricing, weight, and inventory status remain ultra-readable under harsh fluorescent store lights or direct sunlight.
- **Friendly Pragmatism:** Playful color accents that double as functional semantic markers—instantly communicating currency mode switches ($ USD vs. Bs VES), credit balances (*fiao*), scale weights, and offline/Bluetooth hardware states.

## Colors

The palette draws vitality from tropical street markets, fresh produce, and local commerce, structured over warm, non-fatiguing paper-like backdrops.

- **Primary (`#0F9D58` - Market Emerald):** Anchors main actions, sales completions, profit indicators, and positive cash flow. Deep enough to deliver high contrast on light backgrounds.
- **Secondary (`#F59E0B` - Marigold Gold):** Used for customer credit (*fiao*), promotional alerts, quick-tagging, and pending transactions.
- **Tertiary (`#6366F1` - Wholesale Violet):** Reserved for wholesale margins, bulk pricing tiers, and vendor invoice reconciliation.
- **Neutral (`#1E293B` - Deep Bodega Slate):** Grounding color for typography, ensuring immediate scanning legibility on sub-optimal mobile displays. Secondary neutral text sits at `#64748B`.
- **Canvas Base:** Soft warm cream (`#FBF9F5`) as the base wallpaper, crisp white (`#FFFFFF`) for elevated cards, and `#EAE5DE` for delicate, tactile border contours.
- **Hardware & Status Accents:** Sky Blue (`#0284C7`) represents physical hardware connectivity (Bluetooth receipt printers, barcode scanners, sync status), while Coral Red (`#EF4444`) signals stock alerts, expired goods, and overdue debt balances.

## Typography

The typography leverages **Plus Jakarta Sans** across all roles to achieve geometric clarity with human warmth. It features open counters, distinct terminals, and friendly curves that prevent eye fatigue during rapid, 12-hour retail shifts.

- **Tabular Figures (`tnum`):** All numeric displays, currency sums, barcode lookups, and inventory weights must strictly utilize tabular/monospaced numeral features to eliminate horizontal jitter when values increment rapidly during POS checkout.
- **Dual Currency Hierarchy:** The primary currency is styled using `currency-display` in bold contrast, immediately accompanied by the secondary converted value (`currency-sub`) in medium slate (`#64748B`) to keep dual-pricing ambiguity at zero.
- **Display & Labels:** Headlines are tight and punchy with slightly negative letter spacing for immediate visual impact. Small badges and functional category chips use bold, uppercase-leaning labels with generous letter spacing to guarantee scanability on low-resolution hardware.

## Layout & Spacing

The layout is built mobile-first, prioritizing one-handed thumb interaction during active physical cashiering.

- **Grid Architecture:** A fluid 4-column layout on mobile devices reflowing to an 8-column layout on counter tablets (landscape POS mode). Gutters are snug (`0.75rem` on mobile, `1rem` on tablet) to maximize product catalog density without feeling cramped.
- **Cashier Ergonomics (Thumb Zone):** High-frequency operations (cart sum, primary checkout button, barcode trigger, dual-currency toggle) are docked strictly to the bottom 40% of the viewport. Secondary filters and search bars remain at the top.
- **Spacing Rhythm:** Standard layout uses an 8px base grid rhythm (`0.5rem`). Tight element pairs (such as product name to SKU or price tag to weight unit) utilize `space-xs` (4px) and `space-sm` (8px). Structural cards and modular sections maintain `space-lg` (20px) for breathable visual grouping.

## Elevation & Depth

This design system embraces a **Warm Tactile Depth** model, steering clear of pure flat minimalism and heavy blurred glassmorphism:

- **Surface Layering:** The primary viewport rests on the warm cream canvas (`#FBF9F5`). Interactive surfaces and product cards are rendered in pure white (`#FFFFFF`) with a 1px border colored in `#EAE5DE`.
- **Soft Ambient Tinted Shadows:** Shadows use warm amber-slate undertones rather than cold grays, simulating natural daylight:
  - *Resting card:* `0px 2px 4px rgba(30, 41, 59, 0.04), 0px 1px 2px rgba(30, 41, 59, 0.02)`
  - *Floating action/checkout bar:* `0px 8px 24px -4px rgba(30, 41, 59, 0.08), 0px 4px 8px -2px rgba(30, 41, 59, 0.04)`
  - *Modal bottom sheets:* `0px -8px 32px rgba(30, 41, 59, 0.12)`
- **Pressed Tactile Feedback:** On tap or press, interactive cards and buttons compress subtly: `transform: scale(0.98)` paired with an inner inset border highlight, giving the tactile sensation of physical register keys.

## Shapes

The shape system utilizes a welcoming, organic roundedness that feels friendly, modern, and sturdy.

- **Standard Elements (`rounded-2xl` / 16px):** Used for cards, list group items, modal sheet containers, and numeric keypad buttons.
- **Pills & Badges (`rounded-full` / 9999px):** Applied to currency toggle badges, weight fraction chips (`1/2 kg`, `250 gr`), status pills, and floating action triggers.
- **Sub-elements (`rounded-lg` / 8px):** Reserved for small internal icons, input field frames, and item thumbnails.

## Components

### Buttons
- **Primary Checkout Button:** Large, minimum 52px height, full-width thumb anchor. Filled with Market Emerald (`#0F9D58`), bold white text, rounded to 16px. Contains dual-state content: item quantity badge on the left, action label center, and total balance right.
- **Secondary & Utility Buttons:** Warm cream surface (`#F4EFE6`) with dark slate label, flat with a 1px border (`#EAE5DE`).
- **Tactile Keypad Keys:** Raised numeric blocks for fast cash entry with immediate active-state down-press feedback.

### Dual-Currency Pills & Weight Badges
- **Currency Switcher:** A segmented pill container showing `$ USD` and `Bs VES`. The active currency is highlighted with Market Emerald or Bodega Slate with high-contrast text, while the converted secondary value stays visible in subtle contrast beneath it.
- **Weight Fraction Chips:** Quick-tap chips (`100g`, `250g`, `1/2 kg`, `1 kg`, `Pza`) designed for quick weighing-scale sales, utilizing light amber/cream borders that turn solid yellow-orange (`#F59E0B`) when selected.

### Product & Inventory Cards
- **Grid POS Card:** Square aspect ratio with a subtle 1px border (`#EAE5DE`). Shows product name (2 lines max), prominent price tag in bold, and a floating counter badge if items are in the active cart.
- **Inventory List Row:** High-density row with thumbnail, stock level chip (green for healthy, yellow for low, coral red for exhausted), wholesale cost vs. retail price, and quick-swipe actions for restock or price adjustments.

### Inputs & Number Fields
- **Retail Cash Input:** Oversized numerical input with pre-fixed currency symbols, auto-focused numpad, and instant change-calculation pill (*Vuelto*).
- **Search Bar:** Pill-shaped, light warm gray fill (`#F3EFEA`), integrated barcode scanner icon on the right trailing edge for instant camera scanning.

### Bottom Sheets & Modals
- **Quick Action Bottom Sheet:** Slides up with 24px top radiuses, holding customer debt registers (*Cuaderno de Fiao*), quick expense tracking, and custom discount inputs. Features a thick, rounded drag handle.

### Hardware Status Banner
- **Bluetooth & Sync Pill:** Compact pill badge at the top header displaying a Bluetooth icon and tiny pulse dot. Sky Blue (`#0284C7`) indicates active thermal printer connection; muted gray indicates offline local-storage mode.