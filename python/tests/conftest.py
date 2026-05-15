import pytest


@pytest.fixture
def creation_jdn() -> int:
    """JDN of Maya creation date 0.0.0.0.0 (4 Ajaw 8 Kumk'u)."""
    return 584283


@pytest.fixture
def frankie_jdn() -> int:
    """JDN of 1988-12-07 (Frankie Fang birthdate)."""
    return 2447503
